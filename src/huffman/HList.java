package huffman;

import java.util.HashMap;
import java.util.Map.Entry;

import util.BinarySortedTree;

public class HList<S>{
	private HashMap<S, HNode<S>> map;
	
	public HList(){
		map = new HashMap<S, HNode<S>>();
	}
	
	public void add(S symbol){
		HNode<S> node = map.get(symbol);
		if(node==null){
			node = new HNode<S>(symbol);
			map.put(symbol, node);
		}
		else
			node.increment();
	}
	
	public BinarySortedTree<HNode<S>> getAll(){
		BinarySortedTree<HNode<S>> set = new BinarySortedTree<HNode<S>>();
		for(Entry<S,HNode<S>> e:map.entrySet()){
			set.add(e.getValue());
		}
		return set;
	}
	
	
}
