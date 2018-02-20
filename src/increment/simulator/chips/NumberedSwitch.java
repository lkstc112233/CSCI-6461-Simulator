package increment.simulator.chips;

import increment.simulator.SingleCable;

/**
 * A switch that gives numeric output on its output.<br>
 * 
 * The switch has one output: <br>
 * 		* output[width] value ranging from 0 to 2^width - 1.
 * @author Xu Ke
 *
 */
public class NumberedSwitch extends Chip {
	/**
	 * Stores the selected value.
	 */
	protected SingleCable value;
	/**
	 * Constructor.
	 * @param width
	 */
	public NumberedSwitch(int width) {
		value = new SingleCable(width);
		addPort("output", width);
	}
	/**
	 * Put a value into the switch. A.K.A. turning the switch.
	 * @param i
	 */
	public void setValue(int i) {
		value.putValue(i);
	}

	/**
	 * Gets what value is in switch.
	 */
	public Integer getValue() {
		return (int) value.toInteger();
	}
	/**
	 * Move selected value to output.
	 */
	@Override
	public boolean evaluate() {
		return getPort("output").assign(value);
	}

	
}
