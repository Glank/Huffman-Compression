import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import compression.huffman.HuffmanCompressor;

public class GUIDecopressor {
	public static void main(String[] args) throws Throwable{
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File fileIn = chooser.getSelectedFile();
		FileInputStream fis = new FileInputStream(fileIn);
		byte[] bytes = new byte[(int)fileIn.length()];
		fis.read(bytes);
		bytes = HuffmanCompressor.compress(bytes);
		Iterable<Byte> data = HuffmanCompressor.decompress(bytes);
		File fileOut = new File(fileIn.getParent()+File.separator+"d"+fileIn.getName());
		FileOutputStream fos = new FileOutputStream(fileOut);
		for(Byte b:data)
			fos.write(b);
		fos.flush();
		fos.close();
	}
}
