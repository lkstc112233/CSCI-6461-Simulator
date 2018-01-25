package increment.simulator;

/**
 * The control unit. It controls how everything else works, such as write signals, or who is to use the bus.
 * 
 * The controlUnit has one input:
 * 		* opcode[6]
 * 
 * And it has several outputs connecting to every part in the CPU chip.
 * 
 * @author Xu Ke
 *
 */
public class ControlUnit extends Chip {
	/**
	 * Indicating the current processor status.
	 * Member format:
	 * 		StateName_PhaseName, all caps.
	 * @author Xu Ke
	 *
	 */
	private enum Status{
		INITIALIZED,
		FETCH_PC_TO_MAR,
		FETCH_MEMORY_ACCESS,
		FETCH_MBR_TO_IR,
		DECODE,
	}
	Status status;
	
	private boolean ticked = false;
	public ControlUnit() {
		status = Status.INITIALIZED;
		addInput("opcode", 6);
		addOutput("PC_write", 1);
		addOutput("PC_output", 1);
		addOutput("MAR_write", 1);
		addOutput("memory_read", 1);
		addOutput("memory_write", 1);
		addOutput("MBR_output",1);
		addOutput("IR_write", 1);
	}
	/**
	 * Resets all outputs to zero.
	 * So after calling this, we only have to set those should be 1 to 1.
	 */
	protected void resetOutputs() {
		for (String name : outputs.keySet()){
			getOutput(name).setZero();
		}
	}
	/**
	 * This is when the status changes.
	 */
	public void tick(){
		ticked = true;
		switch (status){
		case INITIALIZED:
			status = Status.FETCH_PC_TO_MAR;
			break;
		case FETCH_PC_TO_MAR:
			status = Status.FETCH_MEMORY_ACCESS;
			break;
		case FETCH_MEMORY_ACCESS:
			status = Status.FETCH_MBR_TO_IR;
			break;
		case FETCH_MBR_TO_IR:
			status = Status.DECODE;
			break;
		}
	}
	
	/**
	 * The control Unit will perform an action based on current status every tick.
	 */
	public boolean evaluate(){
		if (!ticked)
			return false;
		ticked = false;
		resetOutputs();
		switch (status){
		case FETCH_PC_TO_MAR:
			getOutput("PC_output").putValue(1);
			getOutput("MAR_write").putValue(1);
			break;
		case FETCH_MEMORY_ACCESS:
			getOutput("memory_read").putValue(1);
			break;
		case FETCH_MBR_TO_IR:
			getOutput("MBR_output").putValue(1);
			getOutput("IR_write").putValue(1);
			break;
		}
		return true;
	}
}
