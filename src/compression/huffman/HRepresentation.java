package compression.huffman;

import java.util.LinkedList;
import java.util.Queue;

public class HRepresentation<S> {
	public Queue<Boolean> bits = new LinkedList<Boolean>();
	public S symbol;
}
