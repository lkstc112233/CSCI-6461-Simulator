package increment.simulator;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import increment.simulator.util.ConvenientStreamTokenizer;
import static increment.simulator.util.ExceptionHandling.panic;

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
	/**
	 * A helper class for state switches.
	 * @author Xu Ke
	 *
	 */
	private class StateConverter {
		private Map<Integer, String> convertTable = new HashMap<>();
		private String defaultState = null;
		public String nextState(int opcode) {
			if (convertTable.containsKey(opcode))
				return convertTable.get(opcode);
			return defaultState;
		}
		public void addConvertPlan(int opcode, String targetState) {
			convertTable.put(opcode, targetState);
		}
		public void addDefaultConvertingStatePlan(String defaultState) {
			this.defaultState = defaultState;
		}
	}
	
	private String currentState = null;
	private Map<String, StateConverter> stateConvertations = new HashMap<>();
	private Map<String, Set<String>> portConvertations = new HashMap<>();
	
	private boolean ticked = false;
	private HashSet<String> inputPortNames;
	public ControlUnit() {
		try {
			loadFile();
		} catch (IOException e) {
			System.out.println("Error parsing control unit definition. Please check file existence.");
			System.exit(-1);
		} catch (IllegalStateException e) {
			System.err.println("Configuration file format error:");
			System.err.println(e.getMessage());
			System.exit(-1);
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
	 * @throws IOException When file error.
	 * @throws IllegalStateException When file format error.
	 */
	private void loadFile() throws IOException {
		ConvenientStreamTokenizer tokens = new ConvenientStreamTokenizer(new FileReader("controlDef.ini"));

		if (!parsePorts(tokens))
			panic("Cannot parse ports.\nLine: " + tokens.lineno());
		if (!parseStatesConversionRules(tokens))
			panic("Cannot parse state rules.\nLine: " + tokens.lineno());
		if (!parsePortsOutputRules(tokens))
			panic("Cannot parse output rules.\nLine: " + tokens.lineno());
	}
	/**
	 * Parses ports.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parsePorts(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() == '{') {
			while (parsePort(tokens));
		}
		return tokens.nextToken() == '}';
	}
	/**
	 * Parses port.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parsePort(ConvenientStreamTokenizer tokens) throws IOException {
		int token = tokens.nextToken();
		if (token == ConvenientStreamTokenizer.TT_WORD) {
			addPort(tokens.sval, 1);
			return true;
		}else if (token == '}') {
			tokens.pushBack();
			return false;
		}
		panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
		return false;
	}
	/**
	 * Parses states and conversion rules.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parseStatesConversionRules(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() == '{') {
			while (parseStateConversionRule(tokens));
		}
		return tokens.nextToken() == '}';
	}
	/**
	 * Parses one single state conversion rule.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parseStateConversionRule(ConvenientStreamTokenizer tokens) throws IOException {
		List<String> baseStates = parseBaseStates(tokens);
		if (baseStates == null)
			return false;
		int token = tokens.nextToken();
		if (token != ':')
			panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
		StateConverter converter = parseTargetStates(tokens);
		if (converter == null)
			panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
		for (String base : baseStates) {
			stateConvertations.put(base, converter);
		}
		return true;
	}
	/**
	 * Parses a base state list.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private List<String> parseBaseStates(ConvenientStreamTokenizer tokens) throws IOException {
		List<String> result = null;
		int token = tokens.nextToken();
		if (token == '{') {
			// A base states list.
			result = new ArrayList<>();
			String state = null;
			while ((state = parseWord(tokens)) != null) {
				result.add(state);
			}
			if ((token = tokens.nextToken()) == '}')
				return result;
			else
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
		} else if (token == ConvenientStreamTokenizer.TT_WORD) {
			// A single base state.
			result = new ArrayList<>();
			result.add(tokens.sval);
		}
		else 
			tokens.pushBack();
		return result;
	}
	/**
	 * Parses a single state.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private String parseWord(ConvenientStreamTokenizer tokens) throws IOException {
		int token = tokens.nextToken();
		if (token == ConvenientStreamTokenizer.TT_WORD) {
			return tokens.sval;
		} else {
			tokens.pushBack();
			return null;
		}
	}
	/**
	 * Parses a target state list
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private StateConverter parseTargetStates(ConvenientStreamTokenizer tokens) throws IOException {
		StateConverter result = null;
		int token = tokens.nextToken();
		if (token == '{') {
			// A target states list.
			result = new StateConverter();
			while (parseTargetPairOrDefaultTarget(tokens, result));
			if ((token = tokens.nextToken()) == '}')
				return result;
			else
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
		} else if (token == ConvenientStreamTokenizer.TT_WORD) {
			// A single base state.
			result = new StateConverter();
			result.addDefaultConvertingStatePlan(tokens.sval);
		}
		else 
			tokens.pushBack();
		return result;
	}
	/**
	 * Parse a target rule.
	 * @param tokens
	 * @param converter
	 * @return
	 * @throws IOException
	 */
	private boolean parseTargetPairOrDefaultTarget(ConvenientStreamTokenizer tokens, StateConverter converter) throws IOException {
		int token = tokens.nextToken();
		if (token == ConvenientStreamTokenizer.TT_NUMBER) {
			int opcode = (int) tokens.nval;
			if (tokens.nextToken() != ':')
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be ':'.");
			String target = parseWord(tokens);
			if (target == null)
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
			converter.addConvertPlan(opcode, target);
			return true;
		} else if (token == ConvenientStreamTokenizer.TT_WORD) {
			converter.addDefaultConvertingStatePlan(tokens.sval);
			return true;
		} else {
			tokens.pushBack();
			return false;
		}	
	}
	/**
	 * Parses state port output rules.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parsePortsOutputRules(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() == '{') {
			while (parsePortsOutputRule(tokens));
		}
		return tokens.nextToken() == '}';
	}
	/**
	 * Parses single port output rule.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private boolean parsePortsOutputRule(ConvenientStreamTokenizer tokens) throws IOException {
		String state = parseWord(tokens);
		if (state == null)
			return false;
		portConvertations.put(state, new HashSet<String>());
		if (tokens.nextToken() != ':')
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno());
		String targetPort = parseWord(tokens);
		if (targetPort == null)
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno());
		do {
			portConvertations.get(state).add(targetPort);
			targetPort = parseNextPort(tokens);
		}while(targetPort != null);
		return true;
	}
	/**
	 * Parses another port. Skips comma.
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private String parseNextPort(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ',') {
			tokens.pushBack();
			return null;
		}
		String port = parseWord(tokens);
		if (port == null)	
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno());
		return port;
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
