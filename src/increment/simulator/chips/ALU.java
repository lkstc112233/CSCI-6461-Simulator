package increment.simulator.chips;
/**
 * A combination of Arithmetic Unit and Logical Unit.
 * 
 * Takes 3 inputs:
 * 		* operand1[16]
 * 		* operand2[16]
 * 		* opcode[6]
 * Gives serveral outputs:
 * 		* result[16]
 * 		* CCStat[4]
 * 
 * @author Xu Ke
 *
 */
public class ALU extends ChipsSet {
	public ALU() {
		addPort("operand1", 16);
		addPort("operand2", 16);
		addPort("opcode", 6);
		addPort("result", 16);
		addPort("CCStat", 4);
		
	}
}
