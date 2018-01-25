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
	 * 		StateNameOrInstructionName_PhaseName, all caps.
	 * @author Xu Ke
	 *
	 */
	private enum Status{
		INITIALIZED,
		FETCH_PC_TO_MAR,
		FETCH_MEMORY_ACCESS,
		FETCH_MBR_TO_IR,
		DECODE,
		LDR_PUT_EA_TO_MAR,
		LDR_MEMORY_ACCESS,
		LDR_MBR_TO_REGISTER,
		STR_PUT_EA_TO_MAR,
		STR_REGISTER_TO_MBR,
		STR_MEMORY_ACCESS,
		LDA_PUT_EA_TO_REGISTER,
		LDX_PUT_EA_TO_MAR,
		LDX_MEMORY_ACCESS,
		LDX_MBR_TO_REGISTER,
		STX_PUT_EA_TO_MAR,
		STX_REGISTER_TO_MBR,
		STX_MEMORY_ACCESS,
		UPDATE_PC,
		HALT,
	}
	Status status;
	
	private boolean ticked = false;
	public ControlUnit() {
		status = Status.INITIALIZED;
		addInput("opcode", 6);
		addOutput("PC_write", 1);
		addOutput("PC_output", 1);
		addOutput("MAR_write", 1);
		addOutput("MBR_input_sel", 1);
		addOutput("memory_read", 1);
		addOutput("memory_write", 1);
		addOutput("MBR_output",1);
		addOutput("IR_write", 1);
		addOutput("EA_Gate", 1);
		addOutput("GPRF_write", 1);
		addOutput("GPRF_output", 1);
		addOutput("IRF_write", 1);
		addOutput("IRF_only", 1);
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
		case DECODE:
			switch((int) getInput("opcode").toInteger()) {
			case 0x00: // HLT
				status = Status.HALT;
				break;
			case 0x01: // LDR
				status = Status.LDR_PUT_EA_TO_MAR;
				break;
			case 0x02: // STR
				status = Status.STR_PUT_EA_TO_MAR;
				break;
			case 0x03: // LDA
				status = Status.LDA_PUT_EA_TO_REGISTER;
				break;
			case 0x21: // LDX
				status = Status.LDX_PUT_EA_TO_MAR;
				break;
			case 0x22: // STX
				status = Status.STX_PUT_EA_TO_MAR;
				break;
			}
			System.out.println((int) getInput("opcode").toInteger());
			break;
		case LDR_PUT_EA_TO_MAR:
			status = Status.LDR_MEMORY_ACCESS;
			break;
		case LDR_MEMORY_ACCESS:
			status = Status.LDR_MBR_TO_REGISTER;
			break;
		case LDR_MBR_TO_REGISTER:
			status = Status.UPDATE_PC;
			break;
		case STR_PUT_EA_TO_MAR:
			status = Status.STR_REGISTER_TO_MBR;
			break;
		case STR_REGISTER_TO_MBR:
			status = Status.STR_MEMORY_ACCESS;
			break;
		case STR_MEMORY_ACCESS:
			status = Status.UPDATE_PC;
			break;
		case LDA_PUT_EA_TO_REGISTER:
			status = Status.UPDATE_PC;
			break;
		case LDX_PUT_EA_TO_MAR:
			status = Status.LDX_MEMORY_ACCESS;
			break;
		case LDX_MEMORY_ACCESS:
			status = Status.LDX_MBR_TO_REGISTER;
			break;
		case LDX_MBR_TO_REGISTER:
			status = Status.UPDATE_PC;
			break;
		case STX_PUT_EA_TO_MAR:
			status = Status.STX_REGISTER_TO_MBR;
			break;
		case STX_REGISTER_TO_MBR:
			status = Status.STX_MEMORY_ACCESS;
			break;
		case STX_MEMORY_ACCESS:
			status = Status.UPDATE_PC;
			break;
		case UPDATE_PC:
			status = Status.FETCH_PC_TO_MAR;
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
		case DECODE:
			break;
		case LDR_PUT_EA_TO_MAR:
			getOutput("EA_Gate").putValue(1);
			getOutput("MAR_write").putValue(1);
			break;
		case LDR_MEMORY_ACCESS:
			getOutput("memory_read").putValue(1);
			break;
		case LDR_MBR_TO_REGISTER:
			getOutput("MBR_output").putValue(1);
			getOutput("GPRF_write").putValue(1);
			break;
		case STR_PUT_EA_TO_MAR:
			getOutput("EA_Gate").putValue(1);
			getOutput("MAR_write").putValue(1);
			break;
		case STR_REGISTER_TO_MBR:
			getOutput("memory_read").putValue(1);
			getOutput("GPRF_output").putValue(1);
			getOutput("MBR_input_sel").putValue(1);
			break;
		case STR_MEMORY_ACCESS:
			getOutput("memory_write").putValue(1);
			break;
		case LDA_PUT_EA_TO_REGISTER:
			getOutput("EA_Gate").putValue(1);
			getOutput("GPRF_write").putValue(1);
			break;
		case LDX_PUT_EA_TO_MAR:
			getOutput("EA_Gate").putValue(1);
			getOutput("MAR_write").putValue(1);
			break;
		case LDX_MEMORY_ACCESS:
			getOutput("memory_read").putValue(1);
			break;
		case LDX_MBR_TO_REGISTER:
			getOutput("MBR_output").putValue(1);
			getOutput("IRF_write").putValue(1);
			break;
		case STX_PUT_EA_TO_MAR:
			getOutput("EA_Gate").putValue(1);
			getOutput("MAR_write").putValue(1);
			break;
		case STX_REGISTER_TO_MBR:
			getOutput("memory_read").putValue(1);
			getOutput("IRF_only").putValue(1);
			getOutput("MBR_input_sel").putValue(1);
			break;
		case STX_MEMORY_ACCESS:
			getOutput("memory_write").putValue(1);
			break;
		case UPDATE_PC:
			getOutput("PC_write").putValue(1);
			break;
		}
		return true;
	}
}
