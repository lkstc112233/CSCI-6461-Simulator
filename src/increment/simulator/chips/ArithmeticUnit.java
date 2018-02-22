package increment.simulator.chips;

/**
 * The arithmetic unit. Inside an ALU probably.
 * 
 * The arithmetic unit takes 3 inputs:
 * 		* operand1[width]
 * 		* operand2[width]
 * 		* opcode[TBD] - specifies which operation to take place.
 * The arithmetic unit gives 1 output:
 * 		* result[width]
 * 		* condition[4] - the condition code.
 * 
 * @author Xu Ke
 *
 */
public class ArithmeticUnit extends Chip {
	public ArithmeticUnit(int width) {
		addPort("operand1", width);
		addPort("operand2", width);
		addPort("result", width);
		addPort("opcode", 4 /* maybe? */);
		addPort("condition", 4);
	}
	
	@Override
	public boolean evaluate() {
		// TODO: put everything here.
		return false;
	}
}
