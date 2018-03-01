package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.SingleCable;

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
		
		Mux resmux = new Mux(6, 16);
		Mux ccmux = new Mux(6, 4);
		addChip(resmux);
		addChip(ccmux);
		addChipPortRelation("result", resmux, "output");
		addChipPortRelation("CCStat", ccmux, "output");
		addChipPortRelation("opcode", resmux, "sel");
		addChipPortRelation("opcode", ccmux, "sel");
		
		ArithmeticUnit au = new ArithmeticUnit(16);
		addChip(au);
		addChipPortRelation("operand1", au, "operand1");
		addChipPortRelation("operand2", au, "operand2");
		addChipPortRelation("opcode", au, "opcode");
		
		Cable cable = new SingleCable(16);
		au.connectPort("result", cable);
		resmux.connectPort("input4", cable);
		resmux.connectPort("input5", cable);
		resmux.connectPort("input6", cable);
		resmux.connectPort("input7", cable);
		
	}
}
