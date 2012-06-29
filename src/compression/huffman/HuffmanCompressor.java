package compression.huffman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import compression.io.BitIOFactory;
import compression.io.BitInputStream;
import compression.io.BitOutputStream;
import compression.io.ByteIOFactory;
import compression.util.ByteIterableArrayWrapper;



public class HuffmanCompressor {
	public static <S> byte[] compress(Iterable<S> data, BitIOFactory<S> factory){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BitOutputStream bos = new BitOutputStream(baos);
		DataOutputStream dos = new DataOutputStream(bos);
		System.out.println("Creating List...");
		HList<S> list = new HList<S>();
		long dataLength = 0;
		for(S s:data){
			list.add(s);
			dataLength++;
		}
		System.out.println("Creating Tree...");
		HTree<S> tree = new HTree<S>(list.getAll());
		list = null;
		HTreeIOFactory<S> treeFactory = new HTreeIOFactory<S>(factory);
		try{
			System.out.println("Writing dataLength: " + dataLength);
			dos.writeLong(dataLength);
			System.out.println("Writing tree...");
			treeFactory.write(tree, bos);
			Map<S, boolean[]> writingMap = tree.compileWritingMap();
			tree = null;
			System.out.println("Writing Data...");
			for(S s:data){
				boolean[] bit = writingMap.get(s);
				for(boolean b:bit)
					bos.write(b);
			}
			bos.flush();
		}
		catch(IOException ioe){
			throw new RuntimeException("Compression Error");
		}
		return baos.toByteArray();
	}
	
	public static byte[] compress(byte[] data){
		return compress(new ByteIterableArrayWrapper(data), new ByteIOFactory());
	}
	
	public static Iterable<Byte> decompress(byte[] data){		
		return decompress(data, new ByteIOFactory());
	}
	
	public static <S> Iterable<S> decompress(byte[] data, BitIOFactory<S> factory){
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		BitInputStream bis = new BitInputStream(bais);
		DataInputStream dis = new DataInputStream(bis);
		HTreeIOFactory<S> treeFactory = new HTreeIOFactory<S>(factory);
		HTree<S> tree = null;
		LinkedList<S> ret = new LinkedList<S>();
		try{
			long dataLength = dis.readLong();
			System.out.println("Read dataLength: " + dataLength);
			System.out.println("Reading tree...");
			tree = treeFactory.read(bis);
			S s;
			System.out.println("Reading data...");
			while(dataLength>0){
				dataLength--;
				s = tree.readFrom(bis);
				ret.add(s);
			}
		}
		catch(IOException ioe){
			throw new RuntimeException("Decompression Error");
		}
		return ret;
	}
}
