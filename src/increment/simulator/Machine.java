package increment.simulator;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import increment.simulator.tools.AssemblyCompiler;
import increment.simulator.tools.AssemblyCompiler.CompiledProgram;
import increment.simulator.util.ConvenientStreamTokenizer;
import static increment.simulator.util.ExceptionHandling.panic;

/**
 * A simulated machine.
 * @author Xu Ke
 *
 */
public class Machine {
	public Machine() {
		try {
			loadFile();
		} catch (IOException e) {
			System.err.println("Configuration file not found.");
			System.exit(-1);
		} catch (IllegalStateException e) {
			System.err.println("Configuration file format error:");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		Memory mem = (Memory) getChip("memory");
		
		// SIMULATED Boot Loader: 
		// It loads a testing program into the memory address 0x10, and sets PC to
		// 0x10.
		((ClockRegister)getChip("PC")).setValue(0x10);
		((RegisterFile)getChip("IndexRegisterFile")).setValue(0, 0);
		String program = "LDX 2, 15 LDR 0, 2, 20, 0 LDA 1, 2, 6 HLT";
		CompiledProgram code = AssemblyCompiler.compile(program);
		mem.loadProgram(0x10, code);
		mem.putValue(0x0F, 0x0230); // Data (560) at 0x0F, it's used as an address for IDX
		mem.putValue(0x244, 0x2134); // Data at 580(0x244)
	}
	/**
	 * Loads a configuration file from disk.
	 * Utilizes {@link java.io.StreamTokenizer} to tokenize.
	 * @throws IOException When load file failed.
	 */
	private void loadFile() throws IOException {
		ConvenientStreamTokenizer tokens = new ConvenientStreamTokenizer(new FileReader("chipsDef.ini"));
		parseChipsDefinition(tokens);
		
		int token = tokens.nextToken();
		String chipName = null;
		String chipType = null;
		String cableChipName = null;
		Cable cable = null;
		final int EXPECTING_OPENING_BRACKETS_FOR_CHIPS = 0;
		final int EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME = 1;
		final int EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME_OR_ARGUMENT = 2;
		final int EXPECTING_COLON = 3;
		final int EXPECTING_CHIP_TYPE = 4;
		final int EXPECTING_ARGUMENT = 5;
		final int EXPECTING_OPENING_BRACKETS_FOR_CABLES = 6;
		final int EXPECTING_CLOSING_BRACKETS_OR_CABLE_ELEMENT = 7;
		final int EXPECTING_DOT = 8;
		final int EXPECTING_PORT_NAME = 9;
		final int EXPECTING_CONNECTION_OR_CLOSING_BRACKETS_OR_COLON_OR_CABLE_ELEMENT = 10;
		final int EXPECTING_CABLE_ELEMENT = 11;
		final int EXPECTING_CABLE_NAME = 12;
		int currentStatus = EXPECTING_OPENING_BRACKETS_FOR_CHIPS;
		ArrayList<Object> args = null;
		while (token != ConvenientStreamTokenizer.TT_EOF) {
			switch(currentStatus) {
			case EXPECTING_OPENING_BRACKETS_FOR_CABLES:
			case EXPECTING_OPENING_BRACKETS_FOR_CHIPS:
				if (token != '{')
					panic("Cannot find openning brackets for chips.");
				currentStatus += 1;
				break;
			case EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME:
				if (token == '}') {
					if (chipName != null && chipType != null) {
						chips.put(chipName, ChipFactory.makeChip(chipType, args.toArray()));
					} else if (chipName != null || chipType != null) {
						panic("Syntax error before } on line " + tokens.lineno());
					} 
					currentStatus = EXPECTING_OPENING_BRACKETS_FOR_CABLES;
				} else if (token == ConvenientStreamTokenizer.TT_WORD) {
					chipName = tokens.sval;
					chipType = null;
					args = new ArrayList<>();
					currentStatus = EXPECTING_COLON;
				} else {
					panic("Unexpected token at line "+tokens.lineno() +", '" + ((char)token) + "'");
				}
				break;
			case EXPECTING_COLON:
				if (token != ':') {
					panic("Expecting ':' on line " + tokens.lineno());
				}
				currentStatus = EXPECTING_CHIP_TYPE;
				break;
			case EXPECTING_CHIP_TYPE:
				if (token != ConvenientStreamTokenizer.TT_WORD) {
					panic("Expecting chip type on line " + tokens.lineno());
				}
				chipType = tokens.sval;
				currentStatus = EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME_OR_ARGUMENT;
				break;
			case EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME_OR_ARGUMENT:
				if (token == ',') {
					currentStatus = EXPECTING_ARGUMENT;
				} else if (token == '}') {
					if (chipName != null && chipType != null) {
						chips.put(chipName, ChipFactory.makeChip(chipType, args.toArray()));
					} else if (chipName != null || chipType != null) {
						panic("Syntax error before } on line " + tokens.lineno());
					} 
					currentStatus = EXPECTING_OPENING_BRACKETS_FOR_CABLES;
				} else if (token == ConvenientStreamTokenizer.TT_WORD) {
					chips.put(chipName, ChipFactory.makeChip(chipType, args.toArray()));
					chipType = null;
					chipName = tokens.sval;
					args = new ArrayList<>();
					currentStatus = EXPECTING_COLON;
				} else {
					panic("Unexpected token at line "+tokens.lineno() +", '" + ((char)token) + "'");
				}
				break;
			case EXPECTING_ARGUMENT:
				if (token == ConvenientStreamTokenizer.TT_NUMBER){
					args.add(new Integer((int)tokens.nval));
				}else if (token == ConvenientStreamTokenizer.TT_WORD){
					args.add(tokens.sval);
				}
				currentStatus = EXPECTING_CLOSING_BRACKETS_OR_CHIP_NAME_OR_ARGUMENT;
				break;
			case EXPECTING_CLOSING_BRACKETS_OR_CABLE_ELEMENT:
				if (token == '}') {
					return;
				} else if (token == ConvenientStreamTokenizer.TT_WORD) {
					cable = null;
					cableChipName = tokens.sval;
					currentStatus = EXPECTING_DOT;
				} else {
					panic("Unexpected token at line "+tokens.lineno() +", '" + ((char)token) + "'");
				}
				break;
			case EXPECTING_DOT:
				if (token != '.') {
					panic("Expecting '.' on line " + tokens.lineno());
				}
				currentStatus = EXPECTING_PORT_NAME;
				break;
			case EXPECTING_PORT_NAME:
				if (token != ConvenientStreamTokenizer.TT_WORD) {
					panic("Expecting port name on line " + tokens.lineno());
				}
				Chip c = chips.get(cableChipName);
				if (c == null)
					panic("Undefined chip name " + cableChipName + " on line " + tokens.lineno());
				int width = c.getPortWidth(tokens.sval);
				if (width <= 0)
					panic("Invalid chip port " + tokens.sval +" for chip " + cableChipName + " on line " + tokens.lineno());
				if (cable == null) {
					cable = new SingleCable(width);
					c.connectPort(tokens.sval, cable);
				} else {
					if (width == cable.getWidth())
						c.connectPort(tokens.sval, cable);
					else {
						Cable ca = new CableAdapter(width, cable);
						c.connectPort(tokens.sval, ca);
					}
				}
				currentStatus = EXPECTING_CONNECTION_OR_CLOSING_BRACKETS_OR_COLON_OR_CABLE_ELEMENT;
				break;
			case EXPECTING_CONNECTION_OR_CLOSING_BRACKETS_OR_COLON_OR_CABLE_ELEMENT:
				if (token == '-'){
					currentStatus = EXPECTING_CABLE_ELEMENT;
				}else if (token == ':'){
					currentStatus = EXPECTING_CABLE_NAME;
				}else if (token == '}'){
					return;
				}else if (token == ConvenientStreamTokenizer.TT_WORD){
					cable = null;
					cableChipName = tokens.sval;
					currentStatus = EXPECTING_DOT;
				}else
					panic("Unexpected token at line "+tokens.lineno() +", '" + ((char)token) + "'");
				break;
			case EXPECTING_CABLE_ELEMENT:
				if (token != ConvenientStreamTokenizer.TT_WORD) {
					panic("Expecting port name on line " + tokens.lineno());
				}
				cableChipName = tokens.sval;
				currentStatus = EXPECTING_DOT;
				break;
			case EXPECTING_CABLE_NAME:
				if (token != ConvenientStreamTokenizer.TT_WORD) {
					panic("Expecting port name on line " + tokens.lineno());
				}
				cables.put(tokens.sval, cable);
				cable = null;
				currentStatus = EXPECTING_CLOSING_BRACKETS_OR_CABLE_ELEMENT;
				break;
			}
			token = tokens.nextToken();
		}
	}
	/**
	 * Parser for chips definition.
	 * @param tokens
	 * @throws IOException
	 */
	private void parseChipsDefinition(ConvenientStreamTokenizer tokens) throws IOException{
		if (tokens.nextToken() != '{')
			panic("Cannot find openning brackets for chips.");
		while (parseChip(tokens));
		if (tokens.nextToken() != '}')
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be '}'.");
	}
	private boolean parseChip(ConvenientStreamTokenizer tokens) throws IOException{
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD){
			tokens.pushBack();
			return false;
		}
		String chipName = tokens.sval;	
		if (tokens.nextToken() != ':')
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be '.'.");
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD)
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be typeName.");
		String chipType = tokens.sval;
		List<Object> params = new ArrayList<Object>();
		Object param = null;
		while ((param = parseParam(tokens)) != null)
			params.add(param);
		// Make chip.
		chips.put(chipName, ChipFactory.makeChip(chipType, params.toArray()));
		return true;
	}
	private Object parseParam(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ','){
			tokens.pushBack();
			return null;
		}
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER)
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be param.");
		return (int)tokens.nval;
	}
	private Map<String, Chip> chips = new HashMap<>();
	private Map<String, Cable> cables = new HashMap<>();
	public Chip getChip(String name) {
		return chips.get(name);
	}
	public Cable getCable(String name) {
		return cables.get(name);
	}
	public void tick(){
		for (Map.Entry<String, Chip> e : chips.entrySet()) {
			e.getValue().tick();
		}
	}
	public void evaluate(){
		boolean change = true;
		while (change) {
			change = false;
			for (Map.Entry<String, Chip> e : chips.entrySet()) {
				if (e.getValue().evaluate())
					change = true;
			}
		}
	}
}
