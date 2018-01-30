package increment.simulator.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import increment.simulator.util.ConvenientStreamTokenizer;
/**
 * A program that compiles a testing program.
 * @author Xu Ke
 *
 */
public class AssemblyCompiler {
	public static class CompiledProgram {
		
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
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		
	}
}
