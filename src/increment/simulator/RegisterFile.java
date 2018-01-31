package increment.simulator;
/**
 * Register file. It contains several registers(And according to the documentation, 
 * the number will be 4), each of width ```width```(which will be specified as 16).
 * 
 * A register file will have three inputs:
 * 		* load[1]
 * 		* address[addressWidth]
 * 		* input[width]
 * A register file will have one output:
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class RegisterFile extends Chip {
	protected Mux muxForOutput;
	protected ClockRegister[] data;
	protected Demux demuxForload;
	public RegisterFile(int addressWidth, int width){
		data = new ClockRegister[1 << addressWidth];
		muxForOutput = new Mux(addressWidth, width);
		demuxForload = new Demux(addressWidth, 1);
		// Connect merged chips.
		for (int i = 0; i < data.length; ++i) {
			data[i] = new ClockRegister(width);
			Cable cable = new SingleCable(1);
			demuxForload.connectPort("output" + Integer.toString(i), cable);
			data[i].connectPort("load", cable);
			cable = new SingleCable(width);
			muxForOutput.connectPort("input" + Integer.toString(i), cable);
			data[i].connectPort("output", cable);
		}
	}
	/**
	 * Since this is a merged chip, we connect ports manually.
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
			demuxForload.connectPort("sel", cable);
			break;
		case "load":
			demuxForload.connectPort("input", cable);
			break;
		default:
			super.connectPort(name, cable);
			break;
		}
	}
	/**
	 * Sets a value for a very register.
	 * @param index
	 * @param value
	 */
	public void setValue(int index, long value) {
		data[index].setValue(value);
	}
	/**
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
			return demuxForload.getPortWidth("input");
		default:
			return super.getPortWidth(name);
		}
	}
	
	public void tick(){
		for (Chip c : data)
			c.tick();
	}
	
	public boolean evaluate(){
		boolean vary = false;
		vary |= demuxForload.evaluate();
		for (Chip c : data)
			vary |= c.evaluate();
		vary |= muxForOutput.evaluate();
		return vary;
	}
	
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
