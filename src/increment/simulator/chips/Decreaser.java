package increment.simulator.chips;

import increment.simulator.SingleCable;

/**
 * The decreaser chip. It will increase any value it taken by one and output.
 * 
 * The decreaser will take 1 input:
 * 		* input[width]
 * The decreaser will give 1 output:
 * 		* output[width]
 * 		whose value is input decreased by one.
 * @author Xu Ke
 *
 */
public class Decreaser extends ChipsSet {
	public Decreaser(int width) {
		Adder adder = new Adder(width);
		ConstantChip const1 = new ConstantChip(width, -1);
		addChip(const1);
		addChip(adder);
		addChipPortRelation("input", adder, "operand1");
		addChipPortRelation("output", adder, "result");
		SingleCable connect = new SingleCable(width);
		adder.connectPort("operand2", connect);
		const1.connectPort("output", connect);
	}
}
