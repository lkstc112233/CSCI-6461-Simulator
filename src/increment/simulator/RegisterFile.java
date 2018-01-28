package increment.simulator;
/**
 * Register file. It contains several registers(And according to the documentation, 
 * the number will be 4), each of width ```width```(which will be specified as 16).
 * 
 * A register file will have three inputs:
 * 		* write[1]
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
	protected Demux demuxForWrite;
	public RegisterFile(int addressWidth, int width){
		data = new ClockRegister[1 << addressWidth];
		muxForOutput = new Mux(addressWidth, width);
		demuxForWrite = new Demux(addressWidth, 1);
		// Connect merged chips.
		for (int i = 0; i < data.length; ++i) {
			data[i] = new ClockRegister(width);
			Cable cable = new SingleCable(1);
			demuxForWrite.connectPort("output" + Integer.toString(i), cable);
			data[i].connectPort("write", cable);
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
		if (name.equals("output"))
			muxForOutput.connectPort(name, cable);
		else if (name.equals("input"))
			for (Chip c : data)
				c.connectPort(name, cable);
		else if (name.equals("address")) {
			muxForOutput.connectPort("sel", cable);
			demuxForWrite.connectPort("sel", cable);
		}else if (name.equals("write"))
			demuxForWrite.connectPort("input", cable);
		else
			super.connectPort(name, cable);
	}
	/**
	 * Sets a value for a very register.
	 * @param index
	 * @param value
	 */
	public void setValue(int index, long value) {
		data[index].setValue(value);
	}
	
	public void tick(){
		for (Chip c : data)
			c.tick();
	}
	
	public boolean evaluate(){
		boolean vary = false;
		vary |= demuxForWrite.evaluate();
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
