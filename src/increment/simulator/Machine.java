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
		parseCablesDefinition(tokens);
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
	/**
	 * Parser for chips definition.
	 * @param tokens
	 * @throws IOException
	 */
	private void parseCablesDefinition(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != '{')
			panic("Cannot find openning brackets for cables.");
		while (parseCable(tokens));
		if (tokens.nextToken() != '}')
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be '}'.");
	}
	private boolean parseCable(ConvenientStreamTokenizer tokens) throws IOException {
		Object[] chipPortDef = parseChipPort(tokens);
		if (chipPortDef == null) 
			return false;
		Cable workingCable = new SingleCable(getChip((String)chipPortDef[0]).getPortWidth((String)chipPortDef[1]));
		getChip((String)chipPortDef[0]).connectPort((String)chipPortDef[1], workingCable);
		int token = tokens.nextToken();
		while (token == '-') {
			chipPortDef = parseChipPort(tokens);
			if (chipPortDef == null)
				panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nExpecting: Chip.Port");
			// TODO: check based on [.
			if (getChip((String)chipPortDef[0]).getPortWidth((String)chipPortDef[1]) == workingCable.getWidth())
				getChip((String)chipPortDef[0]).connectPort((String)chipPortDef[1], workingCable);
			else {
				Cable adapter = new CableAdapter(getChip((String)chipPortDef[0]).getPortWidth((String)chipPortDef[1]), workingCable);
				getChip((String)chipPortDef[0]).connectPort((String)chipPortDef[1], adapter);
			}
			token = tokens.nextToken();
		}
		if (token == ':') {
			if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD)
				panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nExpecting: Cable name.");
			cables.put(tokens.sval, workingCable);
			return true;
		}
		tokens.pushBack();
		return true;
	}
	
	private Object[] parseChipPort(ConvenientStreamTokenizer tokens) throws IOException{
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD) {
			tokens.pushBack();
			return null;
		}
		String chipName = tokens.sval;
		if (tokens.nextToken() != '.')
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be '.'.");
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD)
			panic("Unexpected token: \n\t" + (tokens.ttype > 0 ? ((char)tokens.ttype) : tokens.sval) + "\n\tat line " + tokens.lineno()+"\nShould be port name.");
		String portName = tokens.sval;
		return new Object[]{chipName,portName};
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
