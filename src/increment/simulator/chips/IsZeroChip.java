package increment.simulator.chips;
/**
 * This is a chip who determines whether input is all zero or not.
 * 
 * The isZeroChip has one input:
 * 		* input[width]
 * The isZeroChip has one output:
 * 		* isZero[1]
 * This output will be 1 if every bit in the input is zero, 0 otherwise.
 * 
 * @author Xu Ke
 *
 */
public class IsZeroChip extends Chip {
	/**
	 * Constructor
	 */
	public IsZeroChip(int width) {
		addPort("input", width);
		addPort("isZero", 1);
	}
	
	@Override
	public boolean evaluate() {
		if (getPort("input").toInteger() == 0)
			return assignPort("isZero", 1);
		else
			return assignPort("isZero", 0);
	}
}
