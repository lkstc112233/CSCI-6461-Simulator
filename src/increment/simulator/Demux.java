package increment.simulator;
/**
 * A demultiplexer. Takes an input and moves it to one of X outputs. The other outputs will
 * be set to 0 in all bits. 
 * 
 * A demultiplexer will have two inputs:
 * 		* input[width]
 * 		* sel[addressWidth]
 * A demultiplexer will have N outputs:
 * 		* output0[width]
 * 		* output1[width]
 * 		...
 * 		* output##(N-1)[width]
 * where N = 2 ^ addressWidth.
 * @author Xu Ke
 *
 */
public class Demux extends Chip {
	public Demux(int addressWidth, int width){
		for (int i = 0; i < (1 << addressWidth); ++i) 
			addPort("output" + Integer.toString(i), width);
		addPort("input", width);
		addPort("sel", addressWidth);
	}
	/**
	 * Moves input to the selected output. Set all other outputs to 0.
	 */
	public boolean evaluate(){
		String veryName = "output" + Long.toString(getPort("sel").toInteger());
		boolean dest = false;
		for (String name : ports.keySet()){
			if (name.equals(veryName)) {
				dest |= getPort(name).assign(getPort("input"));
			} else if (name.contains("output"))
				dest |= getPort(name).setZero();
		}
		// Only when the output changed, 
		return dest;
	}
}
