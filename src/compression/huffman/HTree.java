package compression.huffman;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import compression.io.BitInputStream;
import compression.util.BinarySortedTree;


public class HTree<S>{
	private BinarySortedTree<HNode<S>> extraRoots;
	private BinarySortedTree<HNode<S>> extraNodes;
	private HNode<S> next;
	private HNode<S> root;
	public HTree(BinarySortedTree<HNode<S>> nodes){
		extraRoots = new BinarySortedTree<HNode<S>>();
		root = null;
		next = nodes.pollFirst();
		extraNodes = nodes;
		while(next!=null || !extraRoots.isEmpty())
			iterateBuild();
		extraRoots = null;
		extraNodes = null;
	}
	
	public S readFrom(BitInputStream bis) throws IOException{
		return root.readFrom(bis);
	}
	
	public HTree(HNode<S> root){
		this.root = root;
	}
	
	public HNode<S> getRoot(){
		return root;
	}
	
	private void iterateBuild(){
		//theres only one node in the tree... so it's the root
		if(root==null && extraNodes.isEmpty() && extraRoots.isEmpty()){
			System.out.println("Setting root...");
			root = next;
			next = null;
			return;
		}
		//create a root
		if(root==null){
			//System.out.println("Creating root...");
			root = new HNode<S>(next, extraNodes.pollFirst());
			next = extraNodes.pollFirst();
			return;
		}
		
		if(next==null){
			combineBranches();
			return;
		}
		
		if(extraRoots.isEmpty()){
			if(extraNodes.isEmpty()){
				extendBranch();
				return;
			}
			if(extraNodes.first().count<=root.count)
				createBranch();
			else
				extendBranch();
			return;
		}
		
		if(extraNodes.isEmpty()){
			if(next.count<=extraRoots.first().count)
				extendBranch();
			else
				combineBranches();
			return;
		}
		
		int bestType = 0;
		int bestCount = next.count+root.count;
		if(extraNodes.first().count+next.count<bestCount){
			bestCount = extraNodes.first().count+next.count;
			bestType = 2;
		}
		if(extraRoots.first().count+root.count<bestCount)
			bestType = 1;
		switch(bestType){
		case 0: extendBranch(); return;
		case 1: combineBranches(); return;
		case 2: createBranch(); return;
		}
	}
	
	private void extendBranch(){
		//System.out.println("Extending a branch...");
		HNode<S> newNode = new HNode<S>(root, next);
		if(!extraNodes.isEmpty())
			next = extraNodes.pollFirst();
		else
			next = null;
		extraRoots.add(newNode);
		root = extraRoots.pollFirst();
	}
	
	private void combineBranches(){
		//System.out.println("Combinting branches...");
		HNode<S> newNode = new HNode<S>(root, extraRoots.pollFirst());
		extraRoots.add(newNode);
		root = extraRoots.pollFirst();
	}
	
	private void createBranch(){
		//System.out.println("Creating a new branch...");
		HNode<S> newNode = new HNode<S>(next, extraNodes.pollFirst());
		if(extraNodes.isEmpty())
			next=null;
		else
			next = extraNodes.pollFirst();
		if(newNode.count<root.count){
			extraRoots.add(root);
			root = newNode;
		}
		else
			extraRoots.add(newNode);
	}
	
	public void printStatus(){
		System.out.println("####-STATUS-####");
		System.out.println("----Root----");
		System.out.println(root);
		System.out.println("----Extra Roots----");
		extraRoots.treePrint();
		System.out.println("----Next----");
		System.out.println(next);
		System.out.println("----Extra Nodes----");
		extraNodes.treePrint();
	}
	
	public Map<S, boolean[]> compileWritingMap(){
		HashMap<S, boolean[]> writingMap = new HashMap<S, boolean[]>();
		root.populateWritingMap(writingMap);
		return writingMap;
	}
}
