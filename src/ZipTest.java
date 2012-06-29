import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ZipTest {
	public static void main(String[] args) throws Throwable{
		// Encode a String into bytes
		 String inputString = "blahblahblah??";
		 byte[] input = inputString.getBytes("UTF-8");

		 // Compress the bytes
		 byte[] output = new byte[100];
		 Deflater compresser = new Deflater();
		 compresser.setInput(input);
		 compresser.finish();
		 int compressedDataLength = compresser.deflate(output);

		 // Decompress the bytes
		 Inflater decompresser = new Inflater();
		 decompresser.setInput(output, 0, compressedDataLength);
		 byte[] result = new byte[100];
		 int resultLength = decompresser.inflate(result);
		 decompresser.end();

		 // Decode the bytes into a String
		 String outputString = new String(result, 0, resultLength, "UTF-8");
		 System.out.println(outputString);
	}
}
