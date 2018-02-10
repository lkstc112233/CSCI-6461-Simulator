package increment.simulator;
/**
 * A switch that gives numeric output on its output.<br>
 * 
 * The switch has one output: <br>
 * 		* output[width] value ranging from 0 to 2^width - 1.
 * @author Xu Ke
 *
 */
public class NumberedSwitch extends Chip {
	protected SingleCable value;
	public NumberedSwitch(int width) {
		value = new SingleCable(width);
		addPort("output", width);
	}
	
	public void setValue(int i) {
		value.putValue(i);
	}
	
	public Integer getValue() {
		return (int) value.toInteger();
	}
	
	@Override
	public boolean evaluate() {
		return getPort("output").assign(value);
	}

	
}
