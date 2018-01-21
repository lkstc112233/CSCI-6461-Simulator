package increment.simulator;
/**
 * This is the instruction decoder. It decodes a 16-bit wide instruction into several parts.
 * 		* OpCode: [0..5]
 * 		* ...other instruction information.
 * 
 * The decoder has one input:
 * 		* input[16]
 * The decoder now has five outputs:
 * 		* opcode[6]
 * 		* IX[2]
 * 		* R[2]
 * 		* I[1]
 * 		* address[5]
 * @author Xu Ke
 *
 */
public class InstructionDecoder extends Chip {
	public InstructionDecoder() {
		addInput("input", 16);
		addOutput("opcode", 6);
		addOutput("IX", 2);
		addOutput("R", 2);
		addOutput("I", 1);
		addOutput("address", 5);
	}
	public void evaluate() {
		outputs.get("opcode").partialAssign(0, inputs.get("input"), 0, 6);
		outputs.get("R").partialAssign(0, inputs.get("input"), 6, 2);
		outputs.get("IX").partialAssign(0, inputs.get("input"), 8, 2);
		outputs.get("I").partialAssign(0, inputs.get("input"), 10, 1);
		outputs.get("address").partialAssign(0, inputs.get("input"), 11, 5);
	}
}
