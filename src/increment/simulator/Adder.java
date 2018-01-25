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
		addInput("operand1", width);
		addInput("operand2", width);
		addOutput("result", width);
	}
	/**
	 * Adds two inputs together.
	 */
	@Override
	public boolean evaluate() {
		long op1 = getInput("operand1").toInteger();
		long op2 = getInput("operand2").toInteger();
		long oldVal = getOutput("result").toInteger();
		getOutput("result").putValue(op1 + op2);
		return getOutput("result").toInteger() != oldVal;
	}
}
