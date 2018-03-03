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
	private NumberedSwitch mlt_lb;
	private NumberedSwitch mlt_hb;
	private NumberedSwitch div_qu;
	private NumberedSwitch div_re;
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
		
		mlt_hb = new NumberedSwitch(width);
		cable = new SingleCable(width);
		mlt_hb.connectPort("output", cable);
		outputMux.connectPort("input16", cable);
		addChip(mlt_hb);
		
		mlt_lb = new NumberedSwitch(width);
		cable = new SingleCable(width);
		mlt_lb.connectPort("output", cable);
		jibaMux.connectPort("input16", cable);
		addChip(mlt_lb);
		
		div_qu = new NumberedSwitch(width);
		cable = new SingleCable(width);
		div_qu.connectPort("output", cable);
		outputMux.connectPort("input17", cable);
		addChip(div_qu);
		
		div_re = new NumberedSwitch(width);
		cable = new SingleCable(width);
		div_re.connectPort("output", cable);
		jibaMux.connectPort("input17", cable);
		addChip(div_re);
		
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
	
	@Override
	public boolean evaluate() {
		long op1 = getPort("operand1").toInteger();
		long op2 = getPort("operand2").toInteger();
		int width = getPort("result").getWidth();
		long mul = op1 * op2;
		mlt_lb.setValue(mul & ((1 << width) - 1));
		mlt_hb.setValue((mul >> width) & ((1 << width) - 1));
		div_qu.setValue((op1 == 0)?0:(op2/op1));
		div_qu.setValue((op1 == 0)?0:(op2%op1));
		return super.evaluate();
	}
}
