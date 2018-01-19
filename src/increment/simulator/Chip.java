package increment.simulator;

import java.util.HashMap;
import java.util.Map;

/**
 * A chip. This chip works as a black box. It takes 0 or more inputs (as cable), and gives
 * 0 or more outputs (also as cable). 
 * 
 * Each input or output has a name, and a specific width.
 * 
 * This Chip class works as an abstract class.
 * 
 * @author Xu Ke
 *
 */
public abstract class Chip {
	public Chip(){
		inputs = new HashMap<>();
	}
	/**
	 * We are using tick here to indicate a clock tick, so we don't have to simulate a 
	 * wiring between clock and each chip.
	 * 
	 * This method only simulates the instant tick happens. It doesn't change any of the 
	 * chip's outputs.
	 * 
	 */
	public void tick(){}
	/**
	 * Instead, this method evaluates any change in the chip (which takes time to happen).
	 * 
	 * Such as a change in the outputs.
	 */
	public void evaluate(){}
	/**
	 * All inputs.
	 */
	protected Map<String, Cable> inputs;
	/**
	 * Adds an input, with the given name and given width. No Inputs Share same name 
	 * allowed. This method should be called only during construction.
	 * @param name
	 * @param width
	 */
	protected void addInput(String name, int width) {
		if (!inputs.containsKey(name)) {
			// We only put new input if there is not an existing one.
			inputs.put(name, new Cable(width));
		}
	}
	/**
	 * All outputs.
	 */
	protected Map<String, Cable> outputs;
	/**
	 * Adds an output, with the given name and given width. No outputs share same name
	 * allowed.
	 * @param name
	 * @param width
	 */
	protected void addOutput(String name, int width) {
		if (!outputs.containsKey(name)) {
			// And we only put new output if there is not an existing one.
			outputs.put(name, new Cable(width));
		}
	}
}
