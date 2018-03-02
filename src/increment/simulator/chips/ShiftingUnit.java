package increment.simulator.chips;

/**
 * The shifting unit. Inside an ALU probably.
 * 
 * The shifting unit takes 3 inputs:
 * 		* operand[width]
 * 		* count[4]
 * 		* shiftOrRotate[1]
 * 		* shiftingInstruction[2]
 * The logical unit gives 1 output:
 * 		* result[width]
 * 
 * @author Xu Ke
 *
 */
public class ShiftingUnit extends Chip {
	public ShiftingUnit(int width) {
		addPort("operand", width);
		addPort("shiftOrRotate", 1);
		addPort("shiftingInstruction", 2);
		addPort("count", 4);
		addPort("result", width);
	}
	@Override
	public boolean evaluate() {
		long operand = getPort("operand").toInteger();
		int width = getPort("operand").getWidth();
		int count = (int) getPort("count").toInteger();
		int command = (int) getPort("shiftingInstruction").toInteger();
		command |= getPort("shiftOrRotate").toInteger() << 2;
		switch(command){
		case 0:
			return assignPort("result", (operand & (1 << (width - 1))) | ((operand & ((1 << (width - 1)) - 1)) >> count) | ((operand & ((1 << count) - 1)) << (width - 1 - count)));
		case 1:
			return assignPort("result", (operand & (1 << (width - 1))) | ((operand & ((1 << (width - 1)) - 1)) >> (width - 1 - count)) | ((operand & ((1 << (width - 1 - count)) - 1)) << count));
		case 2:			
			return assignPort("result", (operand >> count) | ((operand & ((1 << count) - 1)) << (width - count)));
		case 3:
			return assignPort("result", (operand >> (width - count)) | ((operand & ((1 << (width - count)) - 1)) << count));
		case 4:
			while (count > 0) {
				count -= 1;
				operand >>= 1;
				operand |= (operand << 1) & (1 << (width - 1));
			}
			return assignPort("result", operand);
		case 5:
			return assignPort("result", operand << count);
		case 6:
			return assignPort("result", operand >> count);
		case 7:
			return assignPort("result", operand << count);
		}
		return false;
	}
}
