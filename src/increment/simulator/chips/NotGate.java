package increment.simulator.chips;
/**
 * A not gate. It reverses every bit in input and move it to output.<br>
 * 
 * A not gate has one input:<br>
 * 		* input[width]<br>
 * A not gate has one output:<br>
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class NotGate extends Chip {
	/**
	 * Constructor.
	 * @param width
	 */
	public NotGate(int width){
		addPort("input", width);
		addPort("output", width);
	}
	
	/**
	 * Moves data from input to output reversed.
	 */
	@Override
	public boolean evaluate() {
		return getPort("output").assignReverse(getPort("input"));
	}
}
