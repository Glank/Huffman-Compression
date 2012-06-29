package compression.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ByteIterableInputStream extends InputStream{
	
	private Iterator<Byte> bytes;
	
	public ByteIterableInputStream(Iterable<Byte> bytes){
		this.bytes = bytes.iterator();
	}

	@Override
	public int read() throws IOException {
		if(bytes.hasNext()){
			int b = bytes.next();
			b = b&255;
			return b;
		}
		return -1;
	}
}
