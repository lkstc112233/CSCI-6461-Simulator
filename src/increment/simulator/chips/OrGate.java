package increment.simulator.chips;

/**
 * An or gate of X inputs, each of N bits width.
 * 
 * The or gate has X inputs:
 * 		* input0[width]
 * 		* input1[width]
 * 		...
 * 		* inputX[width]
 * where X = 2 ^ addressWidth.
 * The or gate has 1 output:
 * 		* output[width]
 * Each bit in the output will be the <B> or </B> result of the same bit in all the inputs.
 * 
 * @author Xu Ke
 *
 */
public class OrGate extends LogicGateBase {
	/**
	 * Constructor. 
	 * @param width The width of input and output.
	 * @param addressWidth The width of address. 
	 */
	public OrGate(int width, int addressWidth) {
		super(width, addressWidth);
	}
	/**
	 * Default address width: 1, takes 2 inputs.
	 * @param width
	 */
	public OrGate(int width) {
		this(width, 1);
	}


	/**
	 * Perform OR operation.
	 */
	@Override
	protected long process(long base, long operand) {
		return base | operand;
	}
	/**
	 * The base of an AND operation should be all 1s.
	 */
	@Override
	protected long getBase() {
		return 0;
	}
}
