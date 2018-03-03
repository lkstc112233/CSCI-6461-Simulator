package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;
import increment.simulator.SingleCable;

/**
 * A combination of Arithmetic Unit and Logical Unit.
 * 
 * Takes 3 inputs:
 * 		* operand1[16]
 * 		* operand2[16]
 * 		* opcode[6]
 * 		* shifting[2]
 * 		* shiftingCount[4]
 * 		* CC[4]
 * 		* CCCond[2]
 * Gives serveral outputs:
 * 		* result[16]
 * 		* CCStat[4]
 * 		* jump[1]
 * 		* jiba[16]
 * 
 * @author Xu Ke
 *
 */
public class ALU extends ChipsSet {
	ShiftingUnit su;
	public ALU() {
		addPort("operand1", 16);
		addPort("operand2", 16);
		addPort("opcode", 6);
		addPort("shifting", 2);
		addPort("result", 16);
		addPort("CCStat", 4);
		addPort("shiftingCount", 4);
		addPort("jump", 1);
		addPort("jiba", 16);
		addPort("CC", 4);
		addPort("CCCond", 2);
		
		Mux resmux = new Mux(6, 16);
		Mux ccmux = new Mux(6, 4);
		Mux jumpmux = new Mux(6, 1);
		addChip(resmux);
		addChip(ccmux);
		addChip(jumpmux);
		addChipPortRelation("result", resmux, "output");
		addChipPortRelation("CCStat", ccmux, "output");
		addChipPortRelation("jump", jumpmux, "output");
		addChipPortRelation("opcode", resmux, "sel");
		addChipPortRelation("opcode", ccmux, "sel");
		addChipPortRelation("opcode", jumpmux, "sel");
		
		ArithmeticUnit au = new ArithmeticUnit(16);
		addChip(au);
		addChipPortRelation("operand1", au, "operand1");
		addChipPortRelation("operand2", au, "operand2");
		addChipPortRelation("opcode", au, "opcode");
		addChipPortRelation("jiba", au, "jiba");
		
		Cable cable = new SingleCable(16);
		au.connectPort("result", cable);
		resmux.connectPort("input4", cable);
		resmux.connectPort("input5", cable);
		resmux.connectPort("input6", cable);
		resmux.connectPort("input7", cable);
		resmux.connectPort("input14", cable);
		resmux.connectPort("input16", cable);
		resmux.connectPort("input17", cable);
		resmux.connectPort("input18", cable);
		resmux.connectPort("input19", cable);
		resmux.connectPort("input20", cable);
		resmux.connectPort("input21", cable);
		
		su = new ShiftingUnit(16);
		addChip(su);
		addChipPortRelation("shifting", su, "shiftingInstruction");
		addChipPortRelation("operand2", su, "operand");
		addChipPortRelation("shiftingCount", su, "count");
		cable = new SingleCable(16);
		su.connectPort("result", cable);
		resmux.connectPort("input25", cable);
		resmux.connectPort("input26", cable);
		
		LogicalUnit lu = new LogicalUnit(16);
		addChip(lu);
		addChipPortRelation("operand2", lu, "condition");
		addChipPortRelation("opcode", lu, "opcode");
		addChipPortRelation("CC", lu, "CC");
		addChipPortRelation("CCCond", lu, "CCCond");
		cable = new SingleCable(1);
		lu.connectPort("jump", cable);
		jumpmux.connectPort("input8", cable);
		jumpmux.connectPort("input9", cable);
		jumpmux.connectPort("input10", cable);
		jumpmux.connectPort("input11", cable);
		jumpmux.connectPort("input12", cable);
		jumpmux.connectPort("input13", cable);
		jumpmux.connectPort("input14", cable);
		jumpmux.connectPort("input15", cable);
	}
	
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		if (name.equals("opcode")) {
			CablePartialAdapter cpa = new CablePartialAdapter(1, cable);
			su.connectPort("shiftOrRotate", cpa);
		}
	}
}
