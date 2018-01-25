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
		inputsFormat = new HashMap<>();
		outputsFormat = new HashMap<>();
		inputs = new HashMap<>();
		outputs = new HashMap<>();
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
	 * @return true if anything in output has changed.
	 */
	public boolean evaluate(){ return false; }
	/**
	 * All inputs width.
	 */
	protected Map<String, Integer> inputsFormat;
	/**
	 * All inputs cable.
	 */
	protected Map<String, Cable> inputs;
	/**
	 * Adds an input, with the given name and given width. No Inputs Share same name 
	 * allowed. This method should be called only during construction.
	 * @param name
	 * @param width
	 */
	protected void addInput(String name, int width) {
		if (!inputsFormat.containsKey(name)) {
			// We only put new input if there is not an existing one.
			inputsFormat.put(name, width);
			inputs.put(name, new DummyCable(width));
		}
	}
	/**
	 * Connects a cable to an input slot.
	 * @param name
	 * @param cable
	 */
	public void connectInput(String name, Cable cable){
		if (inputsFormat.containsKey(name) && inputsFormat.get(name) == cable.getWidth()){
			inputs.put(name, cable);
		}
		else
			throw new IllegalStateException("Connecting input failed when trying to connect " + name);
	}
	/**
	 * Returns an input cable of the given name.
	 * @param name
	 * @return
	 */
	public Cable getInput(String name) {
		return inputs.get(name);
	}
	/**
	 * All outputs width.
	 */
	protected Map<String, Integer> outputsFormat;
	/**
	 * All outputs cable.
	 */
	protected Map<String, Cable> outputs;
	/**
	 * Adds an output, with the given name and given width. No outputs share same name
	 * allowed.
	 * @param name
	 * @param width
	 */
	protected void addOutput(String name, int width) {
		if (!outputsFormat.containsKey(name)) {
			// And we only put new output if there is not an existing one.
			outputsFormat.put(name, width);
			outputs.put(name, new DummyCable(width));
		}
	}
	/**
	 * Connects a cable to an output slot.
	 * @param name
	 * @param cable
	 */
	public void connectOutput(String name, Cable cable){
		if (outputsFormat.containsKey(name) && outputsFormat.get(name) == cable.getWidth()){
			outputs.put(name, cable);
		}
		else
			throw new IllegalStateException("Connecting input failed when trying to connect " + name);
	}
	/**
	 * Returns an output cable of the given name.
	 * @param name
	 * @return
	 */
	public Cable getOutput(String name) {
		return outputs.get(name);
	}
}
