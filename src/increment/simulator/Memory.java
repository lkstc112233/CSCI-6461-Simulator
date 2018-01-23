package increment.simulator;
/**
 * The memory. It's abstracted as a black box that supports read/write by byte.
 * 
 * @author Xu Ke
 *
 */
public class Memory extends Chip {
	// Memory data stored in a big array. Using cable allow me to handle bits more conveniently. 
	protected Cable[] data;
	/**
	 * Constructor. Creating a 16-bit addressed memory.
	 * A memory will have three inputs:
	 * 		* write[1]
	 * 		* address[12]
	 * 		* input[16] 
	 * A memory will have one output:
	 * 		* output[16]
	 * @param width
	 */
	public Memory(){
		data = new Cable[4096];
		for (int i = 0; i < data.length; ++i)
			data[i] = new SingleCable(16);
		addInput("write", 1);
		addInput("address", 12);
		addInput("input", 16);
		addOutput("output", 16);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data[address].
	 */
	public void tick(){
		if (inputs.get("write").getBit(0)) {
			data[(int) inputs.get("address").toInteger()].assign(inputs.get("input"));
		}
	}
	/**
	 * When evaluates, we move specified data to output.
	 */
	public void evaluate(){
		if (outputs.get("output") != null)
			outputs.get("output").assign(data[(int) inputs.get("address").toInteger()]);
	}
	/**
	 * Turns chip value into a readable way.
	 */
	public String toString() {
		// TODO: To be implemented
		return "Memory chip.";
	}
}
