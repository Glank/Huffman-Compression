
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;

import compression.huffman.HList;
import compression.huffman.HNode;
import compression.huffman.HTree;
import compression.huffman.HTreeIOFactory;
import compression.huffman.HuffmanCompressor;
import compression.io.BitInputStream;
import compression.io.BitOutputStream;
import compression.io.ByteIOFactory;
import compression.util.BinarySortedTree;


//0010 0001 = !
public class Tests {
	/*
	public static void main(String[] args)throws Throwable{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(new File("Bible.txt"));
		int read;
		while((read=fis.read())!=-1)
			baos.write(read);
		fis.close();
		byte[] bytes = baos.toByteArray();
		baos.close();
		fis = null;
		baos = null;
		System.out.println("Creating list...");
		HList<Byte> list = new HList<Byte>();
		for(byte b:bytes)
			list.add(b);
		System.out.println("Created List");
		System.out.println("Making tree...");
		HTree<Byte> tree = new HTree<Byte>(list.getAll());
		System.out.println("____Old Tree____");
		tree.getRoot().print();
		HTreeIOFactory<Byte> factory = new HTreeIOFactory<Byte>(new ByteIOFactory());
		FileOutputStream fos = new FileOutputStream("testTreeSave.dat");
		factory.write(tree, new BitOutputStream(fos));
		fos.close();
		fis = new FileInputStream("testTreeSave.dat");
		tree = factory.read(new BitInputStream(fis));
		System.out.println("____New Tree____");
		tree.getRoot().print();
	}
	//*/
	//* Decompress
	public static void main(String[] args) throws Throwable{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(new File("Bible.cmp"));
		int read;
		while((read=fis.read())!=-1)
			baos.write(read);
		fis.close();
		byte[] bytes = baos.toByteArray();
		baos.close();
		fis = null;
		baos = null;
		System.out.println("Starting decompression...");
		Iterable<Byte> data = HuffmanCompressor.decompress(bytes);
		System.out.println("Finished decompression.");
		FileOutputStream fos = new FileOutputStream(new File("Bible_2.txt"));
		for(Byte b:data){
			fos.write(b);
		}
		fos.flush();
		fos.close();
	}
	//*/
	/* Compress
	public static void main(String[] args) throws Throwable{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(new File("Bible.txt"));
		int read;
		while((read=fis.read())!=-1)
			baos.write(read);
		fis.close();
		byte[] bytes = baos.toByteArray();
		baos.close();
		fis = null;
		baos = null;
		System.out.println("Starting compression...");
		bytes = HuffmanCompressor.compress(bytes);
		System.out.println("Finished compression.");
		FileOutputStream fos = new FileOutputStream(new File("Bible.cmp"));
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		HList<Character> list = new HList<Character>();
		String str = "j'aime aller sur le bord de l'eau les jeudis ou les jours impairs";
		for(char c:str.toCharArray())
			list.add(c);
		for(HNode<Character> n:list.getAll())
			System.out.println(n);
		HTree<Character> tree = new HTree<Character>(list.getAll());
		list = null;
		tree.getRoot().print();
		Map<Character, boolean[]> map = tree.compileWritingMap();
		for(Entry<Character, boolean[]> e:map.entrySet()){
			System.out.print(e.getKey()+": ");
			print(e.getValue());
		}
	}
	
	public static void print(boolean[] binary){
		for(int i = 0; i<binary.length; i++)
			if(binary[i])
				System.out.print("1");
			else
				System.out.print("0");
		System.out.println();
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		BinarySortedTree<Integer> tree = new BinarySortedTree<Integer>();
		tree.add(5);
		tree.add(6);
		tree.add(2);
		tree.add(4);
		tree.add(4);
		tree.add(3);
		for(Integer i:tree)
			System.out.println(i);
		System.out.println();
		tree.treePrint();
		System.out.println();
		while(tree.size()>0){
			System.out.println("Removing: " + tree.pollFirst());
			System.out.println();
			for(Integer i:tree)
				System.out.println(i);
			System.out.println();
			tree.treePrint();
			System.out.println();
		}
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		HList<Byte> list = new HList<Byte>();
		list.add((byte)3);
		list.add((byte)8);
		list.add((byte)7);
		list.add((byte)3);
		list.add((byte)7);
		list.add((byte)7);
		NavigableSet<HNode<Byte>> nodes = list.getAll();
		for(HNode<Byte> b:nodes)
			System.out.println(b.symbol + ": " + b.count);
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BitOutputStream bos = new BitOutputStream(baos);
		bos.write(false);
		bos.write(false);
		bos.write(true);
		bos.write(false);
		bos.write(18);
		bos.write(false);
		bos.write(false);
		bos.write(false);
		bos.write(true);
		bos.close();
		byte[] bytes = baos.toByteArray();
		for(byte b:bytes)
			System.out.println(b);
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{7});
		BitInputStream bis = new BitInputStream(bais);
		Boolean bit;
		int i = 0;
		while((bit=bis.readBit())!=null){
			if(bit)
				System.out.print(1);
			else
				System.out.print(0);
			i++;
			if(i%8==0)
				System.out.println();
		}
	}
	//*/
	/*
	public static void main(String[] args) throws Throwable{
		FileInputStream fis = new FileInputStream(new File("test.txt"));
		BitInputStream bis = new BitInputStream(fis);
		FileOutputStream fos = new FileOutputStream(new File("test2.txt"));
		BitOutputStream bos = new BitOutputStream(fos);
		Boolean bit;
		int i = 0;
		while((bit=bis.readBit())!=null){
			if(bit)
				System.out.print(1);
			else
				System.out.print(0);
			i++;
			if(i%8==0)
				System.out.println();
			bos.write(bit);
		}
		bos.flush();
		bos.close();
	}
	//*/
}
