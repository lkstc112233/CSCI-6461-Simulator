package increment.simulator;

import java.util.Map.Entry;
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
	public LogicGateBase(int width, int addressWidth) {
		int count = 1 << addressWidth;
		for (int i = 0; i < count; ++i) {
			addPort("input" + i, width);
		}
		addPort("output", width);
	}
	/**
	 * Provides a operate base.
	 * @param base
	 * @param operand
	 * @return
	 */
	protected abstract long process(long base, long operand);
	
	/**
	 * Provides a operand base.
	 * @return
	 */
	protected abstract long getBase();
	
	@Override
	public boolean evaluate() {
		long result = getBase();
		for (Entry<String, Cable> name : ports.entrySet()) {
			if (name.getKey().contains("input")) {
				result = process(result, name.getValue().toInteger());
			}
		}
		if (result == getPort("output").toInteger())
			return false;
		getPort("output").putValue(result);
		return true;
	}
}
