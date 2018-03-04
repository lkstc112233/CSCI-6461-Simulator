package increment.simulator.chips;
/**
 * Adder. It adds two values together.<br>
 * 
 * An adder has two inputs:<br>
 * 		* operand1[width]<br>
 * 		* operand2[width]<br>
 * An adder has three outputs:<br>
 * 		* result[width]
 * 		* overflow[1]
 * 		* underflow[1]
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
		addPort("overflow", 1);
		addPort("underflow", 1);
	}
	/**
	 * Adds two inputs together.
	 */
	@Override
	public boolean evaluate() {
		long op1 = getPort("operand1").toInteger();
		long op2 = getPort("operand2").toInteger();
		long INTMIN = 1 << getPortWidth("operand1") - 1;
		boolean changed = false;
		if (op1 + op2 > INTMIN && op1 < INTMIN && op2 < INTMIN)
			changed |= assignPort("overflow", 1);
		else 
			changed |= assignPort("overflow", 0);
		if (op1 + op2 > INTMIN * 2 && op1 > INTMIN && op2 > INTMIN)
			changed |= assignPort("underflow", 1);
		else 
			changed |= assignPort("underflow", 0);
		changed |= assignPort("result", op1 + op2);
		return changed;
	}
}
