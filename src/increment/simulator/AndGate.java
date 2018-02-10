package increment.simulator;

/**
 * An And gate of X inputs, each of N bits width.
 * 
 * The And gate has X inputs:
 * 		* input0[width]
 * 		* input1[width]
 * 		...
 * 		* inputX[width]
 * where X = 2 ^ addressWidth.
 * The And gate has 1 output:
 * 		* output[width]
 * Each bit in the output will be the <B> and </B> result of the same bit in all the inputs.
 * 
 * @author Yuankai Guan
 *
 */
public class AndGate extends LogicGateBase {
	/**
	 * Constructor. 
	 * @param width The width of input and output.
	 * @param addressWidth The width of address. 
	 */
	public AndGate(int width, int addressWidth) {
		super(width, addressWidth);
	}
	/**
	 * Default address width: 1, takes 2 inputs.
	 * @param width
	 */
	public AndGate(int width) {
		this(width, 1);
	}

	/**
	 * Perform AND operation.
	 */
	@Override
	protected long process(long base, long operand) {
		return base & operand;
	}
	/**
	 * The base of an AND operation should be all 1s.
	 */
	@Override
	protected long getBase() {
		return ~0;
	}
}
