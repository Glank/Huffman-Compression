package compression.huffman;

import java.io.IOException;

import compression.io.BitIOFactory;
import compression.io.BitInputStream;
import compression.io.BitOutputStream;


public class HTreeIOFactory<S> implements BitIOFactory<HTree<S>> {

	private BitIOFactory<S> factory;
	
	public HTreeIOFactory(BitIOFactory<S> factory){
		this.factory = factory;
	}
	
	@Override
	public HTree<S> read(BitInputStream bis) throws IOException {
		return new HTree<S>(readNode(bis));		
	}
	
	private HNode<S> readNode(BitInputStream bis) throws IOException{
		if(bis.readBit())
			return new HNode<S>(factory.read(bis));
		//else
		HNode<S> left = readNode(bis);
		HNode<S> right = readNode(bis);
		return new HNode<S>(left,right);
	}

	@Override
	public void write(HTree<S> tree, BitOutputStream bos) throws IOException {
		writeNode(tree.getRoot(), bos);
	}
	
	private void writeNode(HNode<S> node, BitOutputStream bos) throws IOException{
		if(node.isLeaf()){
			bos.write(true);
			factory.write(node.symbol, bos);
		}
		else{
			bos.write(false);
			writeNode(node.left,bos);
			writeNode(node.right,bos);
		}
	}


}
