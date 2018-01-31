package increment.simulator;
/**
 * A multiplexer. Takes X inputs and select one of them.
 * A multiplexer doesn't store anything, so it does nothing during tick.
 * 
 * A multiplexer will have N + 1 inputs:
 * 		* input0[width]
 * 		* input1[width]
 * 		...
 * 		* input##(N-1)[width]
 * 		* sel[addressWidth]
 * where N = 2 ^ addressWidth.
 * A multiplexer will have one output:
 * 		* output[width]
 * 
 * The multiplexer will move ONE of the inputs to output, specified by sel.
 * 
 * @author Xu Ke
 *
 */
public class Mux extends Chip {
	public Mux(int addressWidth, int width){
		for (int i = 0; i < (1 << addressWidth); ++i) 
			addPort("input" + Integer.toString(i), width);
		addPort("sel", addressWidth);
		addPort("output", width);
	}
	/**
	 * Moves the very input to output.
	 */
	public boolean evaluate(){
		return getPort("output").assign(getPort("input" + Long.toString(getPort("sel").toInteger())));
	}
}
