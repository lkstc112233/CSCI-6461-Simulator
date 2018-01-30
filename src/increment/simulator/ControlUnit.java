package increment.simulator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;

import increment.simulator.util.ConvenientStreamTokenizer;

/**
 * The control unit. It controls how everything else works, such as write signals, or who is to use the bus.
 * This design reads a script for status changes inside the ControlUnit, so to enable more flexible design.
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
	private HashSet<String> inputPortNames;
	public ControlUnit() {
		try {
			loadFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		inputPortNames = new HashSet<>();
		status = Status.INITIALIZED;
		addPort("opcode", 6);
		inputPortNames.add("opcode");
		addPort("PC_write", 1);
		addPort("PC_output", 1);
		addPort("MAR_write", 1);
		addPort("MBR_input_sel", 1);
		addPort("memory_read", 1);
		addPort("memory_write", 1);
		addPort("MBR_output",1);
		addPort("IR_write", 1);
		addPort("EA_Gate", 1);
		addPort("GPRF_write", 1);
		addPort("GPRF_output", 1);
		addPort("IRF_write", 1);
		addPort("IRF_only", 1);
	}
	/**
	 * Loads a configuration file and form all logic needed.
	 * This takes place of all the mess.
	 * @throws FileNotFoundException if file not found of course.
	 */
	private void loadFile() throws FileNotFoundException {
		ConvenientStreamTokenizer tokens = new ConvenientStreamTokenizer(new FileReader(""));

		final int EXPECTING_OPENING_BRACKETS_FOR_PORTS_DEFINITIONS = 0;
		final int EXPECTING_CLOSING_BRACKETS_OR_PORT_NAME = 1;
		final int EXPECTING_CLOSING_BRACKETS_OR_ANOTHER_PORT = 2;
		final int EXPECTING_PORT_NAME = 3;
		final int EXPECTING_OPENING_BRACKETS_FOR_STATES_DEFINITIONS = 4;
		final int EXPECTING_CLOSING_BRACKETS_OR_STATE_NAME = 5;
		final int EXPECTING_CLOSING_BRACKETS_OR_ANOTHER_STATE = 6;
		final int EXPECTING_STATE_NAME = 7;
		final int EXPECTING_OPENING_BRACKETS_FOR_STATE_CHANGE_RULES = 8;
		final int EXPECTING_CLOSING_BRACKETS_OR_BASE_STATE = 9;
		final int EXPECTING_BASE_STATE_NAME_OR_CLOSING_BRACKETS = 10;
		final int EXPECTING_CONNECTING_COLON = 11;
		final int EXPECTING_TARGET_STATE = 12;
		final int EXPECTING_CLOSING_BRACKETS_STATE_DEFALUT_CASE_STATE_OR_OPCODE_VALUE = 13;
		final int EXPECTING_TARGET_SPLIT_COLON = 14;
		final int EXPECTING_TARGET_STATE_FOR_GIVEN_OPCODE = 15;
		final int EXPECTING_TARGET_STATE_CLOSING_BRACKETS = 16;
		final int EXPECTING_OPENING_BRACKETS_FOR_STATE_PORT_STATES = 17;
	}
	/**
	 * Resets all outputs to zero.
	 * So after calling this, we only have to set those should be 1 to 1.
	 */
	protected void resetOutputs() {
		for (String name : ports.keySet()){
			if (!inputPortNames.contains(name))
				getPort(name).setZero();
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
			switch((int) getPort("opcode").toInteger()) {
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
			System.out.println((int) getPort("opcode").toInteger());
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
			getPort("PC_output").putValue(1);
			getPort("MAR_write").putValue(1);
			break;
		case FETCH_MEMORY_ACCESS:
			getPort("memory_read").putValue(1);
			break;
		case FETCH_MBR_TO_IR:
			getPort("MBR_output").putValue(1);
			getPort("IR_write").putValue(1);
			break;
		case DECODE:
			break;
		case LDR_PUT_EA_TO_MAR:
			getPort("EA_Gate").putValue(1);
			getPort("MAR_write").putValue(1);
			break;
		case LDR_MEMORY_ACCESS:
			getPort("memory_read").putValue(1);
			break;
		case LDR_MBR_TO_REGISTER:
			getPort("MBR_output").putValue(1);
			getPort("GPRF_write").putValue(1);
			break;
		case STR_PUT_EA_TO_MAR:
			getPort("EA_Gate").putValue(1);
			getPort("MAR_write").putValue(1);
			break;
		case STR_REGISTER_TO_MBR:
			getPort("memory_read").putValue(1);
			getPort("GPRF_output").putValue(1);
			getPort("MBR_input_sel").putValue(1);
			break;
		case STR_MEMORY_ACCESS:
			getPort("memory_write").putValue(1);
			break;
		case LDA_PUT_EA_TO_REGISTER:
			getPort("EA_Gate").putValue(1);
			getPort("GPRF_write").putValue(1);
			break;
		case LDX_PUT_EA_TO_MAR:
			getPort("EA_Gate").putValue(1);
			getPort("MAR_write").putValue(1);
			break;
		case LDX_MEMORY_ACCESS:
			getPort("memory_read").putValue(1);
			break;
		case LDX_MBR_TO_REGISTER:
			getPort("MBR_output").putValue(1);
			getPort("IRF_write").putValue(1);
			break;
		case STX_PUT_EA_TO_MAR:
			getPort("EA_Gate").putValue(1);
			getPort("MAR_write").putValue(1);
			break;
		case STX_REGISTER_TO_MBR:
			getPort("memory_read").putValue(1);
			getPort("IRF_only").putValue(1);
			getPort("MBR_input_sel").putValue(1);
			break;
		case STX_MEMORY_ACCESS:
			getPort("memory_write").putValue(1);
			break;
		case UPDATE_PC:
			getPort("PC_write").putValue(1);
			break;
		}
		return true;
	}
}
