package increment.simulator.chips;

import java.util.Map.Entry;

import increment.simulator.Cable;
/**
 * Provides a base for logic gates(and/or, or so on).
 * 
 * Basically provides a port constructor.
 * 
 * A logic gate has X inputs:
 * 		* input0[width]
 * 		* input1[width]
 * 		...
 * 		* inputX[width]
 * where X = 2 ^ addressWidth.
 * A logic gate has 1 output:
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public abstract class LogicGateBase extends Chip {
	/**
	 * Constructor. 
	 * @param width The width of input and output.
	 * @param addressWidth The width of address. 
	 */
	public LogicGateBase(int width, int addressWidth) {
		int count = 1 << addressWidth;
		for (int i = 0; i < count; ++i) {
			addPort("input" + i, width);
		}
		addPort("output", width);
	}
	/**
	 * Process logic operation between inputs. 
	 * It could be even more complicated operations like add or even multiply.
	 * That would be magic however.
	 */
	@Override
	public boolean evaluate() {
		long result = getBase();
		for (Entry<String, Cable> name : ports.entrySet()) {
			if (name.getKey().contains("input")) {
				result = process(result, name.getValue().toInteger());
			}
		}
		return assignPort("output", result);
	}
	/**
	 * Provides a operate base.
	 * @param base operator base.
	 * @param operand The other operand.
	 * @return
	 */
	protected abstract long process(long base, long operand);
	
	/**
	 * Provides a operand base. Different operations would have different bases.
	 * @return Operand base.
	 */
	protected abstract long getBase();
}
