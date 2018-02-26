package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;
import increment.simulator.SingleCable;

/**
 * The arithmetic unit. Inside an ALU probably.
 * 
 * The arithmetic unit takes 3 inputs:
 * 		* operand1[width]
 * 		* operand2[width]
 * 		* opcode[6] - specifies which operation to take place.
 * The arithmetic unit gives 2 outputs:
 * 		* result[width]
 * 		* condition[4] - the condition code.
 * 
 * @author Xu Ke
 *
 */
public class ArithmeticUnit extends ChipsSet {
	CablePartialAdapter conditionAdapter;
	public ArithmeticUnit(int width) {
		addPort("operand1", width);
		addPort("operand2", width);
		addPort("result", width);
		addPort("opcode", 6);
		addPort("condition", 4);
		
		Mux outputMux = new Mux(6, width);
		addChip(outputMux);
		addChipPortRelation("result", outputMux, "output");
		addChipPortRelation("opcode", outputMux, "sel");
		
		Adder adder = new Adder(width);
		addChip(adder);
		addChipPortRelation("operand1", adder, "operand1");
		addChipPortRelation("operand2", adder, "operand2");
		SingleCable cable = new SingleCable(width);
		
		adder.connectPort("result", cable);
		outputMux.connectPort("input4", cable);
		outputMux.connectPort("input5", cable);
		outputMux.connectPort("input6", cable);
		outputMux.connectPort("input7", cable);
		
		conditionAdapter = new CablePartialAdapter(4, getPort("condition"));
		adder.connectPort("overflow", new CablePartialAdapter(1, conditionAdapter, 0));
		adder.connectPort("underflow", new CablePartialAdapter(1, conditionAdapter, 1));
		
	}
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		conditionAdapter.reMother(getPort("condition"));
	}
}
