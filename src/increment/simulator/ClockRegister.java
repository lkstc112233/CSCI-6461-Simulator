package increment.simulator;

/**
 * A clock register.<br>
 * 
 * A clock register will have two inputs:<br>
 * 		* load[1]<br>
 * 		* input[width]<br>
 * A clock register will have one output:<br>
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class ClockRegister extends Chip {
	/**
	 * A {@link Cable} object to store value. It's very good for moving data. 
	 */
	protected Cable data;
	/**
	 * Constructor. Creating a register of width of width.
	 * @param width
	 */
	public ClockRegister(int width){
		data = new SingleCable(width);
		addPort("load", 1);
		addPort("input", width);
		addPort("output", width);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data.
	 */
	public void tick(){
		if (getPort("load").getBit(0)) {
			data.assign(getPort("input"));
		}
	}
	/**
	 * When evaluates, we move data to output.
	 */
	public boolean evaluate(){
		return getPort("output").assign(data);
	}
	/**
	 * Turns chip value into a readable way.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Register value: ");
		sb.append(data.toInteger());
		return sb.toString();
	}
	/**
	 * Replaces the value inside with i.
	 * @param i
	 */
	public void setValue(long i) {
		data.putValue(i);
	}
}
