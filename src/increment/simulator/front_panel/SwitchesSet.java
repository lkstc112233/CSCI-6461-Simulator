package increment.simulator.front_panel;

import increment.simulator.Cable;
import increment.simulator.CablePartialAdapter;
import increment.simulator.Chip;

/**
 * A set of switches, which all can separately be on and off. It's going to output either true or false, depending 
 * on whether it is turned to on or off.
 * 
 * A switch has one output: <br>
 * 		* output[width] indicating whether it's on or off.
 * @author Xu Ke
 *
 */
public class SwitchesSet extends Chip {
	protected Switch[] switches;
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
		}
	}
	
	@Override
	public void connectPort(String name, Cable cable) {
		super.connectPort(name, cable);
		outputAdapter.reMother(getPort("output"));
	}
	/**
	 * Flips one bit.
	 * @param i
	 * @param b
	 */
	public void flipBit(int i, boolean b) {
		if (i >= 0  && i < switches.length)
			switches[i].flip(b);
	}
	@Override
	public boolean evaluate() {
		boolean changed = false;
		for (Switch s : switches) {
			changed |= s.evaluate();
		}
		return changed;
	}
}
