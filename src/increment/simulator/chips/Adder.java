package increment.simulator.chips;
/**
 * Adder. It adds two values together.<br>
 * 
 * An adder has two inputs:<br>
 * 		* operand1[width]<br>
 * 		* operand2[width]<br>
 * An adder has one output:<br>
 * 		* result[width]
 * 
 * @author Xu Ke
 *
 */
public class Adder extends Chip {
	/**
	 * Constructor
	 * @param width the width of the operands and result.
	 */
	public Adder(int width) {
		addPort("operand1", width);
		addPort("operand2", width);
		addPort("result", width);
	}
	/**
	 * Adds two inputs together.
	 */
	@Override
	public boolean evaluate() {
		long op1 = getPort("operand1").toInteger();
		long op2 = getPort("operand2").toInteger();
		return assignPort("result", op1 + op2);
	}
}
