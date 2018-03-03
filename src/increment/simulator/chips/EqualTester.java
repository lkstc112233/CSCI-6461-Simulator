package increment.simulator.chips;
/**
 * For au. Tests if two inputs are same.
 * @author Xu Ke
 *
 */
public class EqualTester extends Chip {
	public EqualTester(int width) {
		addPort("input0", width);
		addPort("input1", width);
		addPort("same", 1);
	}
	
	@Override
	public boolean evaluate() {
		if (getPort("input0").toInteger() == getPort("input1").toInteger())
			return assignPort("same", 1);
		else
			return assignPort("same", 0);
	}
}
