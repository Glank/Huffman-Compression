package compression.io;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream{
	public byte bufferFilled;
	public int buffer;
	public InputStream in;
	
	public BitInputStream(InputStream in){
		this.in = in;
	}
	
	public Boolean readBit() throws IOException{
		if(buffer==-1)
			return null;
		
		if(bufferFilled==0){
			buffer = in.read();
			if(buffer==-1)
				return null;
			bufferFilled = 7;
		}
		else
			bufferFilled--;
		
		boolean ret = (buffer&128)>0;
		buffer<<=1;
		return ret;
	}
	
	public void close() throws IOException{
		in.close();
	}

	@Override
	public int read() throws IOException {
		if(bufferFilled!=0){
			int byte_ = 0;
			Boolean bit;
			for(int i = 0; i < 8; i++){
				bit = readBit();
				if(bit==null)
					return -1;
				byte_<<=1;
				if(bit)
					byte_++;
			}
			return byte_;
		}
		return in.read();
	}
}
