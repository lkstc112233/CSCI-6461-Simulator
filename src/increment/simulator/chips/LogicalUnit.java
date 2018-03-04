package increment.simulator.chips;

/**
 * The logical unit. Inside an ALU probably.
 * 
 * The logical unit takes 3 inputs:
 * 		* condition[width]
 * 		* opcode[6] - specifies which operation to take place.
 * 		* CC[4]
 * 		* CCCond[2]
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
		addPort("CC", 4);
		addPort("CCCond", 2);
	}
	
	@Override
	public boolean evaluate() {
		long portVal = getPort("condition").toInteger();
		boolean isNeg = getPort("condition").getBit(getPort("condition").getWidth() - 1);
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
		case 10: // JCC
			if ((getPort("CC").getBit((int) getPort("CCCond").toInteger())))
				return assignPort("jump", 1);
			else
				return assignPort("jump", 0);
		case 11: // JMA
			return assignPort("jump", 1);
		case 12: // JSR
			return assignPort("jump", 1);
		case 13: // RFS not implemented here.
		case 14: // SOB
			if (isNeg || portVal == 0)
				return assignPort("jump", 0);
			else
				return assignPort("jump", 1);
		case 15: // JGE
			if (isNeg)
				return assignPort("jump", 0);
			else
				return assignPort("jump", 1);
		default:
			return false;
		}
	}
}
