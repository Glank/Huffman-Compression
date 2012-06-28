package compression.util;

import java.util.Iterator;

public class ByteIterableArrayWrapper implements Iterable<Byte>{
	
	private byte[] array;
	
	public ByteIterableArrayWrapper(byte[] array){
		this.array = array;
	}
	
	@Override
	public Iterator<Byte> iterator() {
		return new Iterator<Byte>(){
			int i = 0;
			@Override
			public boolean hasNext() {
				return i<array.length;
			}

			@Override
			public Byte next() {
				return array[i++];
			}

			@Override
			public void remove() {
				throw new RuntimeException("Method Not Implemented");
			}
		};
	}
}
