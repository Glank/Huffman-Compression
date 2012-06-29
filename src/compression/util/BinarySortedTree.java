package compression.util;

import java.util.Iterator;

public class BinarySortedTree<T extends Comparable<T>> implements Iterable<T>{
	private BSTNode<T> root;
	private BSTNode<T> first;
	private int size = 0;
	
	public void add(T obj){
		size++;
		if(root==null){
			root = new BSTNode<T>(obj);
			first = root;
		}
		else{
			root.add(new BSTNode<T>(obj));
			if(first.previous!=null)
				first = first.previous;
		}
	}
	
	public T first(){
		return first.load;
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public void treePrint(){
		if(root!=null)
			root.treePrint();
	}
	
	public T pollFirst(){
		if(size==0)
			throw new RuntimeException("Empty Tree");
		T ret = first.load;
		size--;
		if(size==0){
			root = null;
			first = null;
		}
		else{
			BSTNode<T> newRoot = first.removeSelf();
			if(newRoot!=null)
				root = newRoot;
			first = first.next;
		}
		return ret;
	}
	
	public int size(){
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new BSTIterator<T>(first);
	}
}

class BSTNode<T extends Comparable<T>>{
	T load;
	BSTNode<T> parent;
	BSTNode<T> left;
	BSTNode<T> right;
	BSTNode<T> next;
	BSTNode<T> previous;
	
	public BSTNode(T load){
		this.load = load;
	}
	
	public void treePrint(){
		if(left!=null)
			left.treePrint();
		for(int i = getDepth(); i>0; i--)
			System.out.print("  ");
		System.out.println(load);
		if(right!=null)
			right.treePrint();
	}
	
	public void add(BSTNode<T> other){
		if(load.compareTo(other.load)>0){
			if(left==null){
				left = other;
				left.parent = this;
				if(previous!=null){
					left.previous = previous;
					previous.next = left;
				}
				previous = left;
				left.next = this;
			}
			else{
				left.add(other);
				if(previous.next!=this)
					previous = previous.next;
			}
		}
		else{
			if(right==null){
				right = other;
				right.parent = this;
				if(next!=null){
					right.next = next;
					next.previous = right;
				}
				next = right;
				right.previous = this;
			}
			else{
				right.add(other);
				if(next.previous!=this)
					next = next.previous;
			}
		}
	}
	
	public BSTNode<T> removeSelf(){
		if(previous!=null)
			previous.next = next;
		if(next!=null)
			next.previous = previous;
		
		//if is root
		if(parent==null){
			if(left==null){
				right.parent = null;
				return right;
			}
			else if(right==null){
				left.parent=null;
				return left;
			}
			left.parent=null;
			left.treeOnlyAdd(right);
			return left;
		}
		
		//if it is a left child
		if(parent.left==this){
			if(left!=null){
				parent.left = left;
				left.parent = parent;
				if(right!=null)
					left.treeOnlyAdd(right);
			}
			else{
				parent.left = right;
				if(right!=null)
					right.parent = parent;
			}
		}
		else{
			if(right!=null){
				parent.right = right;
				right.parent = parent;
				if(left!=null)
					right.treeOnlyAdd(left);
			}
			else{
				parent.right = left;
				if(left!=null)
					left.parent = parent;
			}
		}
		return null;
	}
	
	public int getDepth(){
		int d = 0;
		BSTNode<T> n = this;
		while(n!=null){
			d++;
			n = n.parent;
		}
		return d;
	}
	
	private void treeOnlyAdd(BSTNode<T> other){
		if(load.compareTo(other.load)<0){
			if(left==null){
				left = other;
				left.parent = this;
			}
			else
				left.add(other);
		}
		else{
			if(right==null){
				right = other;
				right.parent = this;
			}
			else
				right.add(other);
		}
	}
}

class BSTIterator<T extends Comparable<T>> implements Iterator<T>{
	
	private BSTNode<T> cur;
	
	public BSTIterator(BSTNode<T> start){
		if(start==null)
			return;
		while(start.previous!=null)
			start = start.previous;
		cur = start;
	}
	
	@Override
	public boolean hasNext() {
		return cur!=null;
	}

	@Override
	public T next() {
		T ret = cur.load;
		cur = cur.next;
		return ret;
	}

	@Override
	public void remove() {
		BSTNode<T> next = cur.next;
		cur.removeSelf();
		cur = next;
	}
}