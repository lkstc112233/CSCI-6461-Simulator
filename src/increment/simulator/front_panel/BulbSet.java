package increment.simulator.front_panel;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;
import increment.simulator.Chip;

/**
 * A group of bulbs, which can be on and off. It's going to take a group of input that is either true or false,
 * indicating whether they are to be turned on or off.
 * 
 * A bulb set has one input: <br>
 * 		* input[width] indicating whether they are on or off.
 * @author Xu Ke
 *
 */
public class BulbSet extends Chip {
	protected IndicatorBulb[] bulbs;
	protected CablePartialAdapter outputAdapter;
	/**
	 * Generates width switches, then connect each of them to the output.
	 * @param width
	 */
	public BulbSet(int width) {
		bulbs = new IndicatorBulb[width];
		addPort("input", width);
		outputAdapter = new CablePartialAdapter(width, getPort("input"));
		for (int i = 0; i < width; ++i) {
			bulbs[i] = new IndicatorBulb();
			bulbs[i].connectPort("input", new CablePartialAdapter(1, outputAdapter, i));
		}
	}
	
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		outputAdapter.reMother(getPort("input"));
	}
	
	public boolean getBit(int i) {
		if (i >= 0 && i < bulbs.length)
			return bulbs[i].isOn();
		return false;
	}
	
	@Override
	public boolean evaluate() {
		for (IndicatorBulb b : bulbs)
			b.evaluate();
		return false;
	}
}
