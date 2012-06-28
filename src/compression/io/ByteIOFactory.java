package compression.io;

import java.io.IOException;

public class ByteIOFactory implements BitIOFactory<Byte>{

	@Override
	public Byte read(BitInputStream bis) throws IOException {
		int out = bis.read();
		if(out==-1)
			return null;
		return (byte)out;
	}

	@Override
	public void write(Byte b, BitOutputStream bos) throws IOException {
		bos.write(b);
	}

}
