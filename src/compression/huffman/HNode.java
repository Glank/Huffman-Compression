package compression.huffman;


import java.io.IOException;
import java.util.HashMap;

import compression.io.BitInputStream;

public class HNode<S> implements Comparable<HNode<S>>{
	public S symbol;
	public int count;
	public HNode<S> parent;
	public HNode<S> left, right;
	
	public HNode(S symbol){
		this.symbol = symbol;
		count = 1;
	}
	
	public boolean isLeaf(){
		return left==null;
	}
	
	public void print(){
		if(isLeaf())
			System.out.println(this);
		else{
			left.print();
			System.out.println(count);
			right.print();
		}
			
	}
	
	public S readFrom(BitInputStream bis) throws IOException{
		if(isLeaf()){
			//System.out.print((char)(byte)(Byte)symbol);
			return symbol;
		}
		Boolean bit = bis.readBit();
		if(bit==null)
			return null;
		//else
		if(bit)
			return right.readFrom(bis);
		//else
		return left.readFrom(bis);
	}
	
	public HNode(int count){
		this.count = count;
	}
	
	public HNode(HNode<S> left, HNode<S> right){
		this.left = left;
		this.right = right;
		count = left.count+right.count;
		left.parent = this;
		right.parent = this;
	}
	
	public void increment(){
		count++;
	}

	@Override
	public int compareTo(HNode<S> o) {
		return count-o.count;
	}
	
	public HRepresentation<S> getRepresentation(){
		if(parent==null)
			return new HRepresentation<S>();
		HRepresentation<S> ret = parent.getRepresentation();
		if(parent.left==this)
			ret.bits.add(false);
		else
			ret.bits.add(true);
		ret.symbol = symbol;
		return ret;
	}
	
	public void populateWritingMap(HashMap<S, boolean[]> writingMap){
		if(isLeaf()){
			HRepresentation<S> rep = getRepresentation();
			boolean[] bits = new boolean[rep.bits.size()];
			int i = 0;
			while(!rep.bits.isEmpty())
				bits[i++] = rep.bits.poll();
			writingMap.put(symbol, bits);
		}
		else{
			left.populateWritingMap(writingMap);
			right.populateWritingMap(writingMap);
		}
	}
	
	public String toString(){
		return "("+symbol+":"+count+")";
	}
}
