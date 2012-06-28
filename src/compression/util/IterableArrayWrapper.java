package compression.util;

import java.util.Iterator;

public class IterableArrayWrapper<T> implements Iterable<T>{

	private T[] array;
	
	public IterableArrayWrapper(T[] array){
		this.array = array;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			int i = 0;
			
			@Override
			public boolean hasNext() {
				return i<array.length;
			}

			@Override
			public T next() {
				return array[i++];
			}

			@Override
			public void remove() {
				throw new RuntimeException("Method Not Implemented.");
			}
			
		};
	}

}


