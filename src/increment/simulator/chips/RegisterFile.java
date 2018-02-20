package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.SingleCable;

/**
 * Register file. It contains several registers(And according to the documentation, 
 * the number will be 4), each has a same bit width (which will be specified as 16).<br>
 * 
 * A register file will have three inputs:<br>
 * 		* load[1]<br>
 * 		* address[addressWidth]<br>
 * 		* input[width]<br>
 * A register file will have one output:<br>
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class RegisterFile extends ChipsSet {
	/**
	 * A mux for selecting output of the registers.
	 */
	protected Mux muxForOutput;
	/**
	 * The working registers.
	 */
	protected ClockRegister[] data;
	/**
	 * A demux for the load bit.
	 */
	protected Demux demuxForLoad;
	/**
	 * Constructor. It connects the registers by built in cable.
	 * @param addressWidth
	 * @param width
	 */
	public RegisterFile(int addressWidth, int width){
		data = new ClockRegister[1 << addressWidth];
		muxForOutput = new Mux(addressWidth, width);
		addChip(muxForOutput);
		demuxForLoad = new Demux(addressWidth, 1);
		addChip(demuxForLoad);
		// Connect merged chips.
		for (int i = 0; i < data.length; ++i) {
			data[i] = new ClockRegister(width);
			Cable cable = new SingleCable(1);
			demuxForLoad.connectPort("output" + Integer.toString(i), cable);
			data[i].connectPort("load", cable);
			cable = new SingleCable(width);
			muxForOutput.connectPort("input" + Integer.toString(i), cable);
			data[i].connectPort("output", cable);
			addChip(data[i]);
			addChipPortRelation("input", data[i], "input");
		}
		addChipPortRelation("output", muxForOutput, "output");
		addChipPortRelation("address", muxForOutput, "sel");
		addChipPortRelation("address", demuxForLoad, "sel");
		addChipPortRelation("load", demuxForLoad, "input");
	}
	/**
	 * Sets a value for a selected register.
	 * @param index - To select the register.
	 * @param value - To put in the register.
	 */
	public void setValue(int index, long value) {
		data[index].setValue(value);
	}
	/**
	 * Provide a nice readable text.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Register File:\n");
		int i = 0;
		for (Chip c : data) {
			sb.append(i++);
			sb.append(": ");
			sb.append(c);
			sb.append('\n');
		}
		return sb.toString();
	}
}
