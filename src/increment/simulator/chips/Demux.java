package increment.simulator.chips;
/**
 * A demultiplexer. Takes an input and moves it to one of X outputs. The other outputs will
 * be set to 0 in all bits. <br>
 * 
 * A demultiplexer will have two inputs:<br>
 * 		* input[width]<br>
 * 		* sel[addressWidth]<br>
 * A demultiplexer will have N outputs:<br>
 * 		* output0[width]<br>
 * 		* output1[width]<br>
 * 		...<br>
 * 		* output##(N-1)[width]<br>
 * where N = 2 ^ addressWidth.<br>
 * @author Xu Ke
 *
 */
public class Demux extends Chip {
	/**
	 * Constructor.
	 * @param addressWidth
	 * @param width
	 */
	public Demux(int addressWidth, int width){
		for (int i = 0; i < (1 << addressWidth); ++i) 
			addPort("output" + Integer.toString(i), width);
		addPort("input", width);
		addPort("sel", addressWidth);
	}
	/**
	 * Moves input to the selected output. Set all other outputs to 0.
	 * @return true if any of the output has changed.
	 */
	@Override
	public boolean evaluate(){
		String veryName = "output" + Long.toString(getPort("sel").toInteger());
		boolean dest = false;
		for (String name : ports.keySet()){
			if (name.equals(veryName)) {
				dest |= getPort(name).assign(getPort("input"));
			} else if (name.contains("output"))
				dest |= getPort(name).setZero();
		}
		return dest;
	}
}
