package increment.simulator;
/**
 * A multiplexer. Takes X inputs and select one of them.<br>
 * A multiplexer doesn't store anything, so it does nothing during tick.<br>
 * 
 * A multiplexer will have N + 1 inputs:<br>
 * 		* input0[width]<br>
 * 		* input1[width]<br>
 * 		...<br>
 * 		* input##(N-1)[width]<br>
 * 		* sel[addressWidth]<br>
 * where N = 2 ^ addressWidth.<br>
 * A multiplexer will have one output:<br>
 * 		* output[width]<br>
 * 
 * The multiplexer will move ONE of the inputs to output, specified by sel.
 * 
 * @author Xu Ke
 *
 */
public class Mux extends Chip {
	/**
	 * Constructor.
	 * @param addressWidth
	 * @param width
	 */
	public Mux(int addressWidth, int width){
		for (int i = 0; i < (1 << addressWidth); ++i) 
			addPort("input" + Integer.toString(i), width);
		addPort("sel", addressWidth);
		addPort("output", width);
	}
	/**
	 * Moves the selected input to output.
	 */
	@Override
	public boolean evaluate(){
		return getPort("output").assign(getPort("input" + Long.toString(getPort("sel").toInteger())));
	}
}
