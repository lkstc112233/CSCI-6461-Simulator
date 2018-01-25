package increment.simulator;

/**
 * A clock register.
 * 
 * A clock register will have two inputs:
 * 		* write[1]
 * 		* input[width]
 * A clock register will have one output:
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class ClockRegister extends Chip {
	// I'm using a Cable class for storage here. It's just because it fits here.
	protected Cable data;
	/**
	 * Constructor. Creating a register of width of ```width```.
	 * A clock register will have two inputs:
	 * 		* write[1]
	 * 		* input[width]
	 * A clock register will have one output:
	 * 		* output[width]
	 * @param width
	 */
	public ClockRegister(int width){
		data = new SingleCable(width);
		addInput("write", 1);
		addInput("input", width);
		addOutput("output", width);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data.
	 */
	public void tick(){
		if (getInput("write").getBit(0)) {
			data.assign(getInput("input"));
		}
	}
	/**
	 * When evaluates, we move data to output.
	 */
	public boolean evaluate(){
		if (getOutput("output").assign(data))
			return true;
		return false;
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
