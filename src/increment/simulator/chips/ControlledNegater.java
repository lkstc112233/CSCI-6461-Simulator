package increment.simulator.chips;

import increment.simulator.SingleCable;

/**
 * A negater who will negate the input only when negate is set to 1.
 * 
 * It has two inputs:
 * 		* input[width]
 * 		* negate[1]
 * It has one output:
 * 		* output[width]
 * @author Xu Ke
 *
 */
public class ControlledNegater extends ChipsSet {
	public ControlledNegater(int width) {
		addPort("input", width);
		addPort("negate", 1);
		addPort("output", width);
		Negater neg = new Negater(width);
		addChip(neg);
		addChipPortRelation("input", neg, "input");
		Mux mux = new Mux(1, width);
		addChip(mux);
		addChipPortRelation("negate", mux, "sel");
		addChipPortRelation("input", mux, "input0");
		addChipPortRelation("output", mux, "output");
		SingleCable cable = new SingleCable(width);
		neg.connectPort("output", cable);
		mux.connectPort("input1", cable);
	}
}
