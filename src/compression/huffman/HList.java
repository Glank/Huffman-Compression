package compression.huffman;

import java.util.HashMap;
import java.util.Map.Entry;

import compression.util.BinarySortedTree;


public class HList<S>{
	private HashMap<S, HNode<S>> map;
	private int size = 0;
	
	public HList(){
		map = new HashMap<S, HNode<S>>();
	}
	
	public void add(S symbol){
		HNode<S> node = map.get(symbol);
		if(node==null){
			size++;
			node = new HNode<S>(symbol);
			map.put(symbol, node);
		}
		else
			node.increment();
	}
	
	public BinarySortedTree<HNode<S>> getAll(){
		System.out.println("List Size: " + size);
		BinarySortedTree<HNode<S>> set = new BinarySortedTree<HNode<S>>();
		for(Entry<S,HNode<S>> e:map.entrySet()){
			set.add(e.getValue());
		}
		return set;
	}
	
	
}
