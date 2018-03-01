package increment.simulator.chips;

/**
 * The logical unit. Inside an ALU probably.
 * 
 * The logical unit takes 3 inputs:
 * 		* condition[width]
 * 		* opcode[6] - specifies which operation to take place.
 * The logical unit gives 1 output:
 * 		* jump[1]
 * 
 * @author Xu Ke
 *
 */
public class LogicalUnit extends Chip {
	public LogicalUnit(int width) {
		addPort("condition", width);
		addPort("jump", 1);
		addPort("opcode", 6);
	}
	
	@Override
	public boolean evaluate() {
		long portVal = getPort("condition").toInteger();
		switch((int)getPort("opcode").toInteger()){
		case 8: // JZ
			if (portVal == 0)
				return assignPort("jump", 1);
			else
				return assignPort("jump", 0);
		case 9: // JNE
			if (portVal != 0)
				return assignPort("jump", 1);
			else
				return assignPort("jump", 0);
		case 10: // JCC TODO: not finished.
		case 11: // JMA
			return assignPort("jump", 1);
		case 12: // JSR TODO: not finished.
		case 13: // RFS TODO: not finished.
		case 14: // SOB TODO: not finished.
		case 15: // JGE
			if (portVal >= 0)
				return assignPort("jump", 1);
			else
				return assignPort("jump", 0);
		default:
			return false;
		}
	}
}
