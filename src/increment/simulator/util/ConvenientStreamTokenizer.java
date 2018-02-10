package increment.simulator.util;

import java.io.Reader;
import java.io.StreamTokenizer;
/**
 * A tokenizer for my file type.
 * @author Xu Ke
 *
 */
public class ConvenientStreamTokenizer extends StreamTokenizer {
	/**
	 * Constructor.
	 * The tokenizer takes alpha and digit and _ as word, parse numbers, parse symbols, ignores new lines, and takes # styled comments.
	 * @param r
	 */
	public ConvenientStreamTokenizer(Reader r) {
		super(r);
		parseNumbers();
		wordChars('_', '_');
		ordinaryChars('"' + 1, '0' - 1);
		ordinaryChars('9' + 1, 'A' - 1);
		ordinaryChars('Z' + 1, '_' - 1);
		eolIsSignificant(false);
		commentChar('#');
	}

}
