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
 * The control unit. It controls how everything else works, such as load signals, or who is to use the bus.<br>
 * This design reads a script for status changes inside the ControlUnit, so to enable more flexible design.<br>
 * 
 * The controlUnit has three inputs: <br>
 * 		* opcode[7], 0:5 is opcode, and 6 is I bit. So, all opcode greater than 64 is the same one with 
 * 					the value 32 lesser.<br>
 * 		* pause[1], if set to true while tick, the control unit will not change its status, and all output will be set to 0.<br>
 * 		* reset[1], if set to true while tick, the control unit will always change its status to the default state.<br>
 * 
 * And it has several outputs connecting to every part in the CPU chip. These outputs are defined in the <i>controlDef.ini</i>.
 * 
 * @author Xu Ke
 *
 */
public class ControlUnit extends Chip {
	/**
	 * A helper class for state switches. It can provide instruction on how to switch state regarding to current <b>opcode</b>.
	 * @author Xu Ke
	 *
	 */
	private class StateConverter {
		/**
		 * The convert table.
		 */
		private Map<Integer, String> convertTable = new HashMap<>();
		/**
		 * One optional default branch.
		 */
		private String defaultState = null;
		/**
		 * Provide instruction on how to switch state regarding to current <b>opcode</b>.
		 * @param opcode
		 * @return
		 */
		public String nextState(int opcode) {
			if (convertTable.containsKey(opcode))
				return convertTable.get(opcode);
			return defaultState;
		}
		/**
		 * Adds a convert plan with given opcode and targetState.
		 * @param opcode
		 * @param targetState
		 */
		public void addConvertPlan(int opcode, String targetState) {
			convertTable.put(opcode, targetState);
		}
		/**
		 * Sets a default state. It's used when all opcode match fail.
		 * @param defaultState
		 */
		public void addDefaultConvertingStatePlan(String defaultState) {
			this.defaultState = defaultState;
		}
	}
	/**
	 * Stores current control unit state.
	 */
	private String currentState = null;
	/**
	 * Stores default control unit state.
	 */
	private String defaultState = null;
	/**
	 * Stores convert rules.
	 */
	private Map<String, StateConverter> stateConvertations = new HashMap<>();
	/**
	 * Stores port rules.
	 */
	private Map<String, Set<String>> portConvertations = new HashMap<>();
	
	/**
	 * Stores if the control unit has ticked. Since the control unit is the most confident unit, it knows what to do and only evaluate once.
	 */
	private boolean ticked = false;
	/**
	 * If control unit is paused, it records this status.
	 */
	private boolean paused = false;
	/**
	 * Records input ports to get a better practice.
	 */
	private HashSet<String> inputPortNames;
	/**
	 * Constructor. Loads file<i> controlDef.ini</i>.
	 */
	public ControlUnit() {
		inputPortNames = new HashSet<>();
		addPort("opcode", 7);
		inputPortNames.add("opcode");
		addPort("pause", 1);
		inputPortNames.add("pause");
		addPort("reset", 1);
		inputPortNames.add("reset");
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
			if (currentState == null)
				currentState = defaultState = tokens.sval;
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
		if (token == '{') {
			// TODO: change these two cases so they share more code.
			List<Integer> opcodes = new ArrayList<>();
			while(tokens.nextToken() == ConvenientStreamTokenizer.TT_NUMBER)
				opcodes.add((int) tokens.nval);
			if (tokens.ttype != '}')
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be '}'.");
			if (tokens.nextToken() != ':')
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be ':'.");
			String target = parseWord(tokens);
			if (target == null)
				panic("Unexpected token: \n\t" + (token > 0 ? ((char)token) : tokens.sval) + "\n\tat line " + tokens.lineno());
			for (Integer i : opcodes)
				converter.addConvertPlan(i, target);
			return true;
		} else if (token == ConvenientStreamTokenizer.TT_NUMBER) {
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
	@Override
	public void tick() {
		ticked = true;
		paused = false;
		if (getPort("pause").getBit(0)) {
			return;
		}
		if (getPort("reset").getBit(0)) {
			currentState = defaultState;
			return;
		}
		StateConverter converter = stateConvertations.get(currentState);
		if (converter != null)
			currentState = converter.nextState((int) getPort("opcode").toInteger());
		if (currentState == null)
			currentState = "INVALID_INSTRUCTION";
	}
	
	/**
	 * The control Unit will perform an action based on current status every tick.
	 */
	@Override
	public boolean evaluate(){
		if (paused)
			if(getPort("pause").getBit(0))
				return false;
			else {
				ticked = true;
				paused = false;
			}
		if (getPort("pause").getBit(0)) {
			paused = true;
			resetOutputs();
			return true;
		}
		if (!ticked)
			return false;
		ticked = false;
		resetOutputs();
		Set<String> converter = portConvertations.get(currentState);
		if (converter != null) {
			for (String port : converter) {
				getPort(port).putValue(1);
			}
		}
		return true;
	}
	/**
	 * Shows current control unit status.
	 */
	@Override
	public String toString() {
		if (getPort("pause").getBit(0))
			return "PAUSED";
		StringBuilder sb = new StringBuilder();
		sb.append("Current Status:\n\t");
		sb.append(currentState);
		return sb.toString();
	}
}
