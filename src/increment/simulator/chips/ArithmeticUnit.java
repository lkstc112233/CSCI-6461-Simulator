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
 * 		* jiba[width]
 * 
 * @author Xu Ke
 *
 */
public class ArithmeticUnit extends ChipsSet {
	private CablePartialAdapter conditionAdapter;
	public ArithmeticUnit(int width) {
		addPort("operand1", width);
		addPort("operand2", width);
		addPort("result", width);
		addPort("opcode", 6);
		addPort("condition", 4);
		addPort("jiba", width);
		
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
		
		Mux jibaMux = new Mux(6, width);
		addChip(jibaMux);
		addChipPortRelation("jiba", jibaMux, "output");
		addChipPortRelation("opcode", jibaMux, "sel");
		
		MulDiver mudi = new MulDiver(width);
		addChip(mudi);
		addChipPortRelation("operand1", mudi, "operand1");
		addChipPortRelation("operand2", mudi, "operand2");
		cable = new SingleCable(width);
		mudi.connectPort("mlt_hb", cable);
		outputMux.connectPort("input16", cable);
		cable = new SingleCable(width);
		mudi.connectPort("mlt_lb", cable);
		jibaMux.connectPort("input16", cable);
		cable = new SingleCable(width);
		mudi.connectPort("div_qu", cable);
		outputMux.connectPort("input17", cable);
		cable = new SingleCable(width);
		mudi.connectPort("div_re", cable);
		jibaMux.connectPort("input17", cable);
		
		AndGate and = new AndGate(16);
		addChip(and);
		addChipPortRelation("operand1", and, "input0");
		addChipPortRelation("operand2", and, "input1");
		cable = new SingleCable(width);
		and.connectPort("output", cable);
		outputMux.connectPort("input19", cable);
		
		OrGate or = new OrGate(16);
		addChip(or);
		addChipPortRelation("operand1", or, "input0");
		addChipPortRelation("operand2", or, "input1");
		cable = new SingleCable(width);
		or.connectPort("output", cable);
		outputMux.connectPort("input20", cable);
		
		NotGate not = new NotGate(16);
		addChip(not);
		addChipPortRelation("operand2", not, "input");
		cable = new SingleCable(width);
		not.connectPort("output", cable);
		outputMux.connectPort("input21", cable);
		
		Decreaser dec = new Decreaser(16);
		addChip(dec);
		addChipPortRelation("operand2", dec, "input");
		cable = new SingleCable(width);
		dec.connectPort("output", cable);
		outputMux.connectPort("input14", cable);
		
		conditionAdapter = new CablePartialAdapter(4, getPort("condition"));
		adder.connectPort("overflow", new CablePartialAdapter(1, conditionAdapter, 0));
		adder.connectPort("underflow", new CablePartialAdapter(1, conditionAdapter, 1));
		// TRR
		EqualTester et = new EqualTester(width);
		addChipPortRelation("operand1", et, "input0");
		addChipPortRelation("operand2", et, "input1");
		et.connectPort("same", new CablePartialAdapter(1, conditionAdapter, 3));
	}
	
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		conditionAdapter.reMother(getPort("condition"));
	}
}
