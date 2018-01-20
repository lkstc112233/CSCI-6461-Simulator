package increment.simulator;

import java.util.ArrayList;

/**
 * A cable. A cable holds several bits on it. A cable has a width indicating how many bits
 * it is holding. 
 * 
 * @author Xu Ke
 *
 */
public class Cable {
	private ArrayList<Bit> bits;
	/**
	 * Constructor.
	 * @param width
	 */
	public Cable(int width) {
		bits = new ArrayList<Bit>();
		for (int i = 0; i < width; ++i)
			bits.add(new Bit());
	}
	/**
	 * replace cable value with another. Note that the input should share the same 
	 * width with this one.
	 * @param input
	 */
	public void assign(Cable input) {
		if (input == null) return;
		if (bits.size() < input.bits.size())
			throw new IllegalStateException("Connecting wrong cables together.");
		for (int i = 0; i < bits.size(); ++i) {
			bits.set(i, input.bits.get(i));
		}
	}
	/**
	 * Returns cable width.
	 * @return cable width
	 */
	public int getWidth() {
		return bits.size();
	}
	/**
	 * Returns value at a specific bit
	 * @param bitPos Position of the bit (0 based)
	 * @return
	 */
	public boolean getBit(int bitPos) {
		return bits.get(bitPos).get();
	}
	/**
	 * Sets a bit to a specific value
	 * @param bitPos
	 */
	public void putBit(int bitPos, boolean val) {
		bits.get(bitPos).put(val);
	}
	/**
	 * Turns cable value into an integer.
	 * If cable width is larger than 64, behavior is undefined.
	 * @return
	 */
	public long toInteger() {
		long result = 0;
		for (Bit b : bits)
		{
			if (b.get())
				result += 1;
			result <<= 1;
		}
		return result;
	}
	/**
	 * Put an integer into the cable.
	 * Uses only low ```width``` bits.
	 * @param value
	 */
	public void putValue(long value) {
		for (int i = bits.size(); i > 0; --i)
		{
			bits.get(i - 1).put((value & 1) == 1);
			value >>= 1;
		}
	}
}
