package increment.simulator.chips;

import java.util.HashMap;
import java.util.Map;

import increment.simulator.Cable;
import increment.simulator.DummyCable;

/**
 * A chip. This chip works as a black box. It takes 0 or more inputs (as {@link Cable}), and gives
 * 0 or more outputs (also as {@link Cable}). <br>
 * 
 * Each port has a name, and a specific width.<br>
 * 
 * This Chip class works as an <b>abstract</b> class.
 * 
 * @author Xu Ke
 *
 */
public abstract class Chip {
	/**
	 * Constructor. Initializes ports tables.
	 */
	public Chip(){
		/*
		portFormat: port name to width 
		ports : define different port in the chip
		
		
		*/
		portsFormat = new HashMap<>();
		ports = new HashMap<>();
	}   
	/**
	 * We are using tick here to indicate a <b>clock tick</b>, so we don't have to simulate a 
	 * wiring between clock and each chip.<br>
	 * 
	 * This method only simulates <b>the clock rising edge</b>. It doesn't change any of the 
	 * chip's outputs.
	 * 
	 */
	public void tick(){}
	/**
	 * Instead, this method evaluates any change in the chip (which takes time to happen).<br>
	 * 
	 * Such as a change in the outputs.
	 * @return true if anything in output has changed.
	 */
	public boolean evaluate(){ return false; }
	/**
	 * All ports width mapping.
	 */
	protected Map<String, Integer> portsFormat;
	/**
	 * All ports cable mapping.
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
	 * Assigning a value into a given port.
	 * @param name
	 * @param value
	 * @return true if the port changes.
	 */
	protected boolean assignPort(String name, long value) {
		long oldValue = getPort(name).toInteger();
		getPort(name).putValue(value);
		return oldValue != getPort(name).toInteger();
	}
	/**
	 * Connects a cable to a port.
	 * @param name - port name.
	 * @param cable - cable to connect.
	 * @throws IllegalStateException When the cable trying to connect mismatches in port width.
	 */
	public void connectPort(String name, Cable cable){
		if (portsFormat.containsKey(name) && portsFormat.get(name) == cable.getWidth()) {
			if (!(ports.get(name) instanceof DummyCable))
				System.err.println("Warning: trying to reconnect port '"+name+"'.\nOld cable overwriten.");
			ports.put(name, cable);
		}
		else
			throw new IllegalStateException("Connecting failed when trying to connect port '" + name +"', whose width is " + portsFormat.get(name) + ", with a cable of width " + cable.getWidth());
	}
	/**
	 * Returns an cable connecting to the port of the given name.
	 * @param name
	 * @return Specified cable.
	 */
	public Cable getPort(String name) {
		return ports.get(name);
	}
	/**
	 * @param name
	 * @return The width of port[name]. <b>-1</b> when the given name not existing.
	 */
	public int getPortWidth(String name) {
		if (portsFormat.containsKey(name))
			return portsFormat.get(name);
		else
			return -1;
	}
}
