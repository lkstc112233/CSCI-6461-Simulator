package increment.simulator;
/**
 * The IO device base.
 * @author Xu Ke
 *
 */
public abstract class IODevice {
	/**
	 * outputs a byte. If the device cannot output, just do nothing. 
	 * @param word - the word to output
	 */
	public void output(short word) {}
	/**
	 * Inputs a byte. If the device cannot perform any input, just return 0. 
	 * @return The byte.
	 */
	public short input() { return 0; }
	/**
	 * Checks the status. 
	 * @return The status.
	 */
	public short status() { return 0; }
	/**
	 * Will be called on the tick this device is active. Just updates the buffer.
	 */
	public void tick() {}
}
