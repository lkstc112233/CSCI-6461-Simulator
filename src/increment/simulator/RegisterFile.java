package increment.simulator;
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
public class RegisterFile extends Chip {
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
		demuxForLoad = new Demux(addressWidth, 1);
		// Connect merged chips.
		for (int i = 0; i < data.length; ++i) {
			data[i] = new ClockRegister(width);
			Cable cable = new SingleCable(1);
			demuxForLoad.connectPort("output" + Integer.toString(i), cable);
			data[i].connectPort("load", cable);
			cable = new SingleCable(width);
			muxForOutput.connectPort("input" + Integer.toString(i), cable);
			data[i].connectPort("output", cable);
		}
	}
	/**
	 * Since this is a merged chip, we should connect ports manually to the right position.
	 */
	@Override
	public void connectPort(String name, Cable cable){
		switch(name){
		case "output":
			muxForOutput.connectPort(name, cable);
			break;
		case "input":
			for (Chip c : data)
				c.connectPort(name, cable);
			break;
		case "address":
			muxForOutput.connectPort("sel", cable);
			demuxForLoad.connectPort("sel", cable);
			break;
		case "load":
			demuxForLoad.connectPort("input", cable);
			break;
		default:
			super.connectPort(name, cable);
			break;
		}
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
	 * Distributes this function to the right chip.
	 * @return real width of a given port.
	 */
	@Override
	public int getPortWidth(String name){
		switch(name){
		case "output":
			return muxForOutput.getPortWidth(name);
		case "input":
			return data[0].getPortWidth(name);
		case "address":
			return muxForOutput.getPortWidth("sel");
		case "load":
			return demuxForLoad.getPortWidth("input");
		default:
			return super.getPortWidth(name);
		}
	}
	/**
	 * Ticks every memory chip.
	 */
	@Override
	public void tick(){
		for (Chip c : data)
			c.tick();
	}
	/**
	 * Puts selected register value to the output.
	 * @return true if any value changed.
	 */
	@Override
	public boolean evaluate(){
		boolean vary = false;
		vary |= demuxForLoad.evaluate();
		for (Chip c : data)
			vary |= c.evaluate();
		vary |= muxForOutput.evaluate();
		return vary;
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
