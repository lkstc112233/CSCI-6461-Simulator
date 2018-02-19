package increment.simulator.chips;
/**
 * A gate will transfer data if the transfer input is set to true.<br>
 * 
 * A gate has two inputs:<br>
 * 		* input[width]<br>
 * 		* transfer[1]<br>
 * A gate has one output:<br>
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class Gate extends Chip {
	/**
	 * Constructor.
	 * @param width
	 */
	public Gate(int width) {
		addPort("input", width);
		addPort("transfer", 1);
		addPort("output", width);
	}
	
	/**
	 * Only modifies output when transfer is set. 
	 */
	@Override
	public boolean evaluate() {
		if (getPort("transfer").getBit(0))
			return getPort("output").assign(getPort("input"));
		return false;
	}
}
