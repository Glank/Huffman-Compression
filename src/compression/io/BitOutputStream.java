package compression.io;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends OutputStream{
	private OutputStream out;
	private byte buffer;
	private byte bufferFilled;
	public BitOutputStream(OutputStream out){
		this.out = out;
	}
	public void write(boolean bit) throws IOException{
		buffer<<=1;
		if(bit)
			buffer++;
		if(bufferFilled==7){
			bufferFilled=0;
			out.write(buffer);
		}
		else
			bufferFilled++;
	}
	public void flush() throws IOException{
		if(bufferFilled!=0){
			buffer<<=8-bufferFilled;
			out.write(buffer);
		}
		out.flush();
	}
	public void close() throws IOException{
		out.close();
	}
	
	@Override
	public void write(int b) throws IOException {
		if(bufferFilled==0)
			out.write(b);
		else{
			byte newBufFill = 8;
			while(bufferFilled!=0){
				newBufFill--;
				write((b&128)>0);
				b<<=1;
			}
			b = (byte)b;
			b>>=8-newBufFill;
			bufferFilled = newBufFill;
			buffer = (byte)b;
		}
	}
}
