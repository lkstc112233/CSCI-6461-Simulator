package increment.simulator.chips;
/**
 * Does the mul and div trick.
 * @author Xu Ke
 *
 */
public class MulDiver extends Chip {
	public MulDiver(int width) {
		addPort("operand1", width);
		addPort("operand2", width);
		addPort("mlt_hb", width);
		addPort("mlt_lb", width);
		addPort("div_qu", width);
		addPort("div_re", width);
	}

	@Override
	public boolean evaluate() {
		long op1 = getPort("operand1").toInteger();
		long op2 = getPort("operand2").toInteger();
		int width = getPort("operand1").getWidth();
		long mul = op1 * op2;
		
		boolean result = false;
		
		result |= assignPort("mlt_lb", mul & ((1 << width) - 1));
		result |= assignPort("mlt_hb", (mul >> width) & ((1 << width) - 1));
		result |= assignPort("div_qu", (op1 == 0)?0:(op2/op1));
		result |= assignPort("div_re", (op1 == 0)?0:(op2%op1));
		
		return result;
	}
}
