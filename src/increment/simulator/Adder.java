package increment.simulator;
/**
 * Adder. It adds two values together.
 * 
 * An adder has two inputs:
 * 		* operand1[width]
 * 		* operand2[width]
 * An adder has one output:
 * 		* result[width]
 * 
 * @author Xu Ke
 *
 */
public class Adder extends Chip {
	/**
	 * 
	 * @param width
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
		long oldVal = getPort("result").toInteger();
		getPort("result").putValue(op1 + op2);
		return getPort("result").toInteger() != oldVal;
	}
}
