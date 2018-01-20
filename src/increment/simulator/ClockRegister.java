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
 * @author lkstc
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
		data = new Cable(width);
		addInput("write", 1);
		addInput("input", width);
		addOutput("output", width);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data.
	 */
	public void tick(){
		if (inputs.get("write").getBit(0)) {
			data.assign(inputs.get("input"));
		}
	}
	/**
	 * When evaluates, we move data to output.
	 */
	public void evaluate(){
		outputs.get("output").assign(data);
	}
	/**
	 * Turns chip value into a readable way.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Reg: ");
		sb.append(data.toInteger());
		return sb.toString();
	}
}
