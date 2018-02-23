package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;

/**
 * A set of switches, which all can separately be on and off. It's going to output either true or false, depending 
 * on whether it is turned to on or off.<br>
 * 
 * A switch has one output: <br>
 * 		* output[width] indicating whether it's on or off.
 * @author Xu Ke
 *
 */
public class SwitchesSet extends ChipsSet {
	/**
	 * Switches storage.
	 */
	protected Switch[] switches;
	/**
	 * Output adapter. Provides a better way organizing the switches.
	 */
	protected CablePartialAdapter outputAdapter;
	/**
	 * Generates width switches, then connect each of them to the output.
	 * @param width
	 */
	public SwitchesSet(int width) {
		switches = new Switch[width];
		addPort("output", width);
		outputAdapter = new CablePartialAdapter(width, getPort("output"));
		for (int i = 0; i < width; ++i) {
			switches[i] = new Switch();
			switches[i].connectPort("output", new CablePartialAdapter(1, outputAdapter, i));
			addChip(switches[i]);
		}
	}
	/**
	 * When connect port we reMother the adapter so the output goes to the right place. 
	 */
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		outputAdapter.reMother(getPort("output"));
	}
	/**
	 * Flips one bit.
	 * @param i - switch index
	 * @param b - bit.
	 */
	public void flipBit(int i, boolean b) {
		if (i >= 0  && i < switches.length)
			switches[i].flip(b);
	}
}
