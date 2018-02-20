package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.SingleCable;

/**
 * A constant chip that has one Output, which produces a constant value.<br>
 * 
 * A constant chip will have one output:<br>
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class ConstantChip extends Chip {
	/**
	 * The constant data for output.
	 */
	private Cable data;
	/**
	 * This is a wrapper to satisfy reflection.
	 * @param width
	 * @param value
	 */
	public ConstantChip(int width, int value) {
		this(width, (long)value);
	}
	/**
	 * Constructor.
	 * @param width
	 * @param value
	 */
	public ConstantChip(int width, long value) {
		data = new SingleCable(width);
		data.putValue(value);
		addPort("output", width);
	}
	/**
	 * Constructor. Where value = 0.
	 * @param width
	 */
	public ConstantChip(int width) {
		this(width, 0l);
	}
	/**
	 * put data to output.
	 */
	@Override
	public boolean evaluate() {
		return getPort("output").assign(data);
	}
}
