package increment.simulator.chips;
/**
 * Reverts the input to its negative form.
 * 
 * The negater has one input:
 * 		* input[width]
 * The negater has one output:
 * 		* output[width]
 * @author Xu Ke
 *
 */
public class Negater extends Chip {
	public Negater(int width) {
		addPort("input", width);
		addPort("output", width);
	}
	
	@Override
	public boolean evaluate() {
		return assignPort("output", -getPort("input").toInteger());
	}
}
