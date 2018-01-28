package increment.simulator;
/**
 * A gate will transfer data if the transfer input is set to true.
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
public class Gate extends Chip {
	public Gate(int width) {
		addPort("input", width);
		addPort("transfer", 1);
		addPort("output", width);
	}
	
	@Override
	public boolean evaluate() {
		if (getPort("transfer").getBit(0))
			if(getPort("output").assign(getPort("input")))
				return true;
		return false;
	}
}
