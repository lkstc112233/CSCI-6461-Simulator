package increment.simulator;
/**
 * A zero gate will transfer data if the transfer input is set to true.
 * Else it will transmits 0 instead.<br>
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
public class ZeroGate extends Chip {
	/**
	 * Constructor.
	 * @param width
	 */
	public ZeroGate(int width) {
		addPort("input", width);
		addPort("transfer", 1);
		addPort("output", width);
	}
	/**
	 * Transfers data if transfer is set to 1.
	 * Otherwise sets output to zero.
	 */
	@Override
	public boolean evaluate() {
		if (getPort("transfer").getBit(0))
			return getPort("output").assign(getPort("input"));
		if (getPort("output").toInteger() == 0) return false;
		getPort("output").setZero();
		return true;
	}
}