package increment.simulator.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import increment.simulator.util.ConvenientStreamTokenizer;
import static increment.simulator.util.ExceptionHandling.panic;
/**
 * A program that compiles a testing program.
 * @author Xu Ke
 *
 */
public class AssemblyCompiler {
	public static class CompiledProgram implements Iterable<Short>{
		private List<Short> program = new ArrayList<>();
		public void addInstruction(short inst) {
			program.add(inst);
		}
		@Override
		public Iterator<Short> iterator() {
			return program.iterator();
		}
		
	}
	public static CompiledProgram compile(String fileNameOrSource, boolean isFile) {
		ConvenientStreamTokenizer tokens = null;
		if (isFile) {
			try {
				tokens = new ConvenientStreamTokenizer(new FileReader(fileNameOrSource));
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.exit(-1);
			}
		}
		else
			tokens = new ConvenientStreamTokenizer(new StringReader(fileNameOrSource));
		CompiledProgram program = null;
		try {
			program = compile(tokens);
		} catch (IOException e) {
			System.err.println("File format error.");
			System.exit(-1);
		}
		return program;
	}
	public static CompiledProgram compile(String source) {
		return compile(source, false);
	}
	private static CompiledProgram compile(ConvenientStreamTokenizer tokens) throws IOException{
		CompiledProgram program = new CompiledProgram();
		int instruction;
		while ((instruction = phaseInstruction(tokens)) > 0)
				program.addInstruction((short) instruction);;
		return program;
	}
	private static int phaseInstruction(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_WORD)
			return -1;
		switch(tokens.sval){
		case "LDR": // 0x01
			return (parseRAndIXAndAddressAndOptionalI(tokens) | (1 << 10));
		case "STR": // 0x02 
			return (parseRAndIXAndAddressAndOptionalI(tokens) | (2 << 10));
		case "LDA": // 0x03 
			return (parseRAndIXAndAddressAndOptionalI(tokens) | (3 << 10));
		case "LDX": // 0x21 
			return (parseIXAndAddressAndOptionalI(tokens) | (33 << 10));
		case "STX": // 0x22
			return (parseIXAndAddressAndOptionalI(tokens) | (34 << 10));
		case "HLT": // 0
			return 0;
		}
		panic("Unrecognized instruction");
		return -1;
	}
	private static short parseRAndIXAndAddressAndOptionalI(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER)
			panic("Unexpected token at line " + tokens.lineno());
		short i = (short) tokens.nval;
		if (tokens.nextToken() != ',')
			panic("Unexpected token at line " + tokens.lineno());
		i <<= 8;
		i |= parseIXAndAddressAndOptionalI(tokens);
		return i;
	}
	private static short parseIXAndAddressAndOptionalI(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER)
			panic("Unexpected token at line " + tokens.lineno());
		short i = (short) tokens.nval;
		if (tokens.nextToken() != ',')
			panic("Unexpected token at line " + tokens.lineno());
		i <<= 6;
		i |= parseAddressAndOptionalI(tokens);
		return i;
	}
	private static short parseAddressAndOptionalI(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER)
			panic("Unexpected token at line " + tokens.lineno());
		short i = (short) tokens.nval;
		i |= parseOptionalI(tokens);
		return i;
	}
	/**
	 * Parses I part in an instruction at bit-5
	 * @param tokens
	 * @return 0 - if there is no I part or I part is 0. <br> 32 - if I part is 1.
	 * @throws IOException
	 */
	private static short parseOptionalI(ConvenientStreamTokenizer tokens) throws IOException {
		if (tokens.nextToken() != ',') {
			tokens.pushBack();
			return 0;
		}
		if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER)
			panic("Unexpected token at line " + tokens.lineno());
		if (1 == (int)tokens.nval)
			return 1 << 5;
		return 0;
	}
	public static void main(String[] args) {
		CompiledProgram cpm = compile("LDX 2,15	LDR 0,2,20,0 LDA 1,2,6,0");
		for (short s : cpm.program)
			System.out.println(s);
	}
}
