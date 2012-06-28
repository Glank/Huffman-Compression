package compression.io;

import java.io.IOException;

public interface BitIOFactory<T> {
	public T read(BitInputStream bis) throws IOException;
	public void write(T obj, BitOutputStream bos) throws IOException;
}
