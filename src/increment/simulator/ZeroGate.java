package increment.simulator;
/**
 * A zero gate will transfer data if the transfer input is set to true.
 * Else it will transmits 0 instead.
 * 
 * A gate has two inputs:
 * 		* input[width]
 * 		* transfer[1]
 * A gate has one output:
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class ZeroGate extends Chip {
	public ZeroGate(int width) {
		addPort("input", width);
		addPort("transfer", 1);
		addPort("output", width);
	}
	
	@Override
	public boolean evaluate() {
		if (getPort("transfer").getBit(0))
			return getPort("output").assign(getPort("input"));
		if (getPort("output").toInteger() == 0) return false;
		getPort("output").setZero();
		return true;
	}
}