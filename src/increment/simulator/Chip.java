package increment.simulator;

import java.util.HashMap;
import java.util.Map;

/**
 * A chip. This chip works as a black box. It takes 0 or more inputs (as cable), and gives
 * 0 or more outputs (also as cable). 
 * 
 * Each port has a name, and a specific width.
 * 
 * This Chip class works as an abstract class.
 * 
 * @author Xu Ke
 *
 */
public abstract class Chip {
	public Chip(){
		portsFormat = new HashMap<>();
		ports = new HashMap<>();
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
	 * All ports width.
	 */
	protected Map<String, Integer> portsFormat;
	/**
	 * All ports cable.
	 */
	protected Map<String, Cable> ports;
	/**
	 * Adds a port, with the given name and given width. No ports share same name 
	 * allowed. This method should be called only during construction.
	 * @param name
	 * @param width
	 */
	protected void addPort(String name, int width) {
		if (!portsFormat.containsKey(name)) {
			// We only put new input if there is not an existing one.
			portsFormat.put(name, width);
			ports.put(name, new DummyCable(width));
		}
	}
	/**
	 * Connects a cable to a port.
	 * @param name
	 * @param cable
	 */
	public void connectPort(String name, Cable cable){
		if (portsFormat.containsKey(name) && portsFormat.get(name) == cable.getWidth()){
			ports.put(name, cable);
		}
		else
			throw new IllegalStateException("Connecting failed when trying to connect port '" + name +"', whose width is " + portsFormat.get(name) + ", with a cable of width " + cable.getWidth());
	}
	/**
	 * Returns an cable connecting to the port of the given name.
	 * @param name
	 * @return
	 */
	public Cable getPort(String name) {
		return ports.get(name);
	}
	/**
	 * @param name
	 * @return The width of port[name]. -1 when the given name not existing.
	 */
	public int getPortWidth(String name) {
		if (portsFormat.containsKey(name))
			return portsFormat.get(name);
		else
			return -1;
	}
}
