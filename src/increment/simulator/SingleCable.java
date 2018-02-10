package increment.simulator;

import java.util.ArrayList;

/**
 * A cable implementation. A cable holds several bits on it. A cable has a width indicating how many bits
 * it is holding. 
 * 
 * @author Xu Ke
 *
 */
public class SingleCable extends Cable {
	/**
	 * Bits storage.
	 */
	private ArrayList<Bit> bits;
	/**
	 * Constructor.
	 * @param width
	 */
	public SingleCable(int width) {
		bits = new ArrayList<Bit>();
		for (int i = 0; i < width; ++i)
			bits.add(new Bit());
	}
	/**
	 * Returns cable width.
	 * @return cable width
	 */
	@Override
	public int getWidth() {
		return bits.size();
	}
	/**
	 * Returns value at a specific bit
	 * @param bitPos Position of the bit (0 based)
	 * @return Bit value if bitPos is valid.<br>false otherwise.
	 */
	@Override
	public boolean getBit(int bitPos) {
		if (bitPos < getWidth() && bitPos >= 0)
			return bits.get(bitPos).get();
		else
			return false;
	}
	/**
	 * Sets a bit to a specific value
	 * @param bitPos
	 */
	@Override
	public void putBit(int bitPos, boolean val) {
		if (bitPos < getWidth() && bitPos >= 0)
			bits.get(bitPos).put(val);
	}
}
