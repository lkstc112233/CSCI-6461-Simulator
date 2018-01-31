package increment.simulator;

import increment.simulator.tools.AssemblyCompiler.CompiledProgram;

/**
 * The memory. It's abstracted as a black box that supports read/load by byte.
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
	 * 		* load[1]
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
		addPort("load", 1);
		addPort("address", 12);
		addPort("input", 16);
		addPort("output", 16);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data[address].
	 */
	public void tick(){
		if (getPort("load").getBit(0)) {
			data[(int) getPort("address").toInteger()].assign(getPort("input"));
		}
	}
	/**
	 * When evaluates, we move specified data to output.
	 */
	public boolean evaluate(){
		return getPort("output").assign(data[(int) getPort("address").toInteger()]);
	}
	/**
	 * Turns chip value into a readable way.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Memory chip data:\n");
		for (int i = 0; i < 4096; ++i) {
			sb.append(i);
			sb.append(": ");
			sb.append(data[i].toInteger());
			sb.append("\n");
		}
		return sb.toString();
	}
	public void putValue(int address, int value) {
		data[address].putValue(value);
	}
	/**
	 * Load a program into memory.
	 * @param address The starting address in memory. 
	 * @param code The compiled program. {@link increment.simulator.tools.AssemblyCompiler}
	 */
	public void loadProgram(int address, CompiledProgram code) {
		for (Short ins : code) {
			putValue(address++, ins);
		}
	}
}
