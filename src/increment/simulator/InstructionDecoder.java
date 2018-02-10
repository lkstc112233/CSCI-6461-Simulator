package increment.simulator;
/**
 * This is the instruction decoder. It decodes a 16-bit wide instruction into several parts.<br>
 * 		* OpCode: [0..5]<br>
 * 		* ...other instruction information.<br>
 * 
 * The decoder has one input:<br>
 * 		* input[16]<br>
 * The decoder now has five outputs:<br>
 * 		* opcode[6]<br>
 * 		* IX[2]<br>
 * 		* R[2]<br>
 * 		* I[1]<br>
 * 		* address[5]<br>
 * @author Xu Ke
 *
 */
public class InstructionDecoder extends Chip {
	/**
	 * Constructor.
	 */
	public InstructionDecoder() {
		addPort("input", 16);
		addPort("opcode", 6);
		addPort("IX", 2);
		addPort("R", 2);
		addPort("I", 1);
		addPort("address", 5);
	}
	/**
	 * Moves decoded instruction to outputs.
	 */
	@Override
	public boolean evaluate() {
		boolean vary = false;
		vary |= getPort("opcode").partialAssign(0, getPort("input"), 10, 6);
		vary |= getPort("R").partialAssign(0, getPort("input"), 8, 2);
		vary |= getPort("IX").partialAssign(0, getPort("input"), 6, 2);
		vary |= getPort("I").partialAssign(0, getPort("input"), 5, 1);
		vary |= getPort("address").partialAssign(0, getPort("input"), 0, 5);
		return vary;
	}
}
