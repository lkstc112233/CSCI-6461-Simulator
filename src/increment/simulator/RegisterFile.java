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
		demuxForWrite = new Demux(addressWidth, width);
		// Connect merged chips.
		for (int i = 0; i < data.length; ++i) {
			data[i] = new ClockRegister(width);
			Cable cable = new SingleCable(1);
			demuxForWrite.connectOutput("output" + Integer.toString(i), cable);
			data[i].connectInput("write", cable);
			cable = new SingleCable(width);
			muxForOutput.connectInput("input" + Integer.toString(i), cable);
			data[i].connectOutput("output", cable);
		}
	}
	/**
	 * Since this is a merged chip, we connect output manually.
	 */
	@Override
	public void connectOutput(String name, Cable cable){
		if (name.equals("output"))
			muxForOutput.connectOutput(name, cable);
		else
			super.connectOutput(name, cable);
	}
	/**
	 * Since this is a merged chip, we connect these inputs manually.
	 */
	@Override
	public void connectInput(String name, Cable cable){
		if (name.equals("input"))
			for (Chip c : data)
				c.connectInput(name, cable);
		else if (name.equals("address")) {
			muxForOutput.connectInput("sel", cable);
			demuxForWrite.connectInput("sel", cable);
		}else if (name.equals("write"))
			demuxForWrite.connectInput("input", cable);
		else
			super.connectOutput(name, cable);
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
}
