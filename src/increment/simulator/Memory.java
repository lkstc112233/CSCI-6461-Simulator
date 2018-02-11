package increment.simulator;

import increment.simulator.tools.AssemblyCompiler.CompiledProgram;

/**
 * The memory. It's abstracted as a black box that supports read/load by byte, a.k.a. a memory interface.<br>
 * A memory will have three inputs:<br>
 * 		* load[1]<br>
 * 		* address[12]<br>
 * 		* input[16] <br>
 * A memory will have one output:<br>
 * 		* output[16]
 * 
 * @author Xu Ke
 *
 */
public class Memory extends Chip {
	/** 
	 * Memory data stored in a big array. Using cable allow me to handle bits more conveniently. 
	 */
	protected Cable[] data;
	/**
	 * Constructor. Creating a 12-bit addressed memory (4096 words, addressing from 0 to 4095).
	 */
	public Memory(){
		this(12);
	}
	/**
	 * Constructor. Creating a width-bit addressed memory.
	 * @param width
	 */
	public Memory(int width) {
		data = new Cable[1 << width];
		changed = new boolean[1 << width];
		for (int i = 0; i < data.length; ++i)
			data[i] = new SingleCable(16);
		addPort("load", 1);
		addPort("address", 1 << width);
		addPort("input", 16);
		addPort("output", 16);
	}
	/**
	 * When timer ticks, if input[0] is true, we move data of input to data[address].
	 */
	@Override
	public void tick(){
		if (getPort("load").getBit(0)) {
			// TODO: add range detect here.
			int address = (int) getPort("address").toInteger();
			data[address].assign(getPort("input"));
			changed[address] = true;
		}
	}
	/**
	 * When evaluates, we move specified data to output.
	 * @return true if value changed.
	 */
	@Override
	public boolean evaluate(){
		return getPort("output").assign(data[(int) getPort("address").toInteger()]);
	}
	
	/**
	 * Only those data assigned will be output during toString.
	 * This array is used to store if those bits are assigned.
	 */
	protected boolean[] changed;
	/**
	 * Turns chip value into a readable way.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Memory chip data:\n");
		for (int i = 0; i < data.length; ++i) {
			if (!changed[i]) continue;
			sb.append(i);
			sb.append(": ");
			sb.append(data[i].toInteger());
			sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * Put value to desired address.
	 * @param address
	 * @param value
	 */
	public void putValue(int address, int value) {
		data[address].putValue(value);
		changed[address] = true;
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
