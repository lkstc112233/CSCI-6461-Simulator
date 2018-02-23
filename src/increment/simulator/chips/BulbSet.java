package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;

/**
 * A group of bulbs, which can be on and off. It's going to take a group of input that is either true or false,
 * indicating whether they are to be turned on or off.<br>
 * 
 * A bulb set has one input: <br>
 * 		* input[width] indicating whether they are on or off.
 * @author Xu Ke
 *
 */
public class BulbSet extends ChipsSet {
	/**
	 * Bulbs storage. 
	 */
	protected IndicatorBulb[] bulbs;
	/**
	 * An adapter to provide a unified interface for input.
	 */
	protected CablePartialAdapter inputAdapter;
	/**
	 * Generates width switches, then connect each of them to the output.
	 * @param Width bits width of the bulb set.
	 */
	public BulbSet(int width) {
		bulbs = new IndicatorBulb[width];
		addPort("input", width);
		inputAdapter = new CablePartialAdapter(width, getPort("input"));
		for (int i = 0; i < width; ++i) {
			bulbs[i] = new IndicatorBulb();
			bulbs[i].connectPort("input", new CablePartialAdapter(1, inputAdapter, i));
			addChip(bulbs[i]);
		}
	}
	/**
	 * Need to reMother the cable so the bulbs takes inputs from the new input.
	 */
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		inputAdapter.reMother(getPort("input"));
	}
	/**
	 * Gets a bulb status at a specific position.
	 * @param i - Bulb index.
	 * @return Bulb status of the specific position.
	 */
	public boolean getBit(int i) {
		if (i >= 0 && i < bulbs.length)
			return bulbs[i].isOn();
		return false;
	}
}
