package compression.huffman;

import java.util.LinkedList;
import java.util.Queue;

public class HRepresentation<S> {
	Queue<Boolean> bits = new LinkedList<Boolean>();
	S symbol;
}
