package increment.simulator;
/**
 * A cable partial adapter. It works like a cable adapter but it only affects the
 * bits it's linked in.
 * @author Xu Ke
 *
 */
public class CablePartialAdapter extends Cable {
	/**
	 * Cable width.
	 */
	int width;
	/**
	 * Cable offset from mother cable.
	 */
	int offset;
	/**
	 * Mother cable.
	 */
	Cable motherCable;
	/**
	 * Constructor.
	 * @param width - Bit width.
	 * @param cableInput - Mother cable
	 * @param offset - default 0.
	 */
	public CablePartialAdapter(int width, Cable cableInput, int offset) {
		this.width = width;
		this.offset = offset;
		this.motherCable = cableInput;
	}
	/**
	 * Constructor. Assigning offset to 0.
	 * @param width - Bit width.
	 * @param cableInput - Mother cable
	 */
	public CablePartialAdapter(int width, Cable cableInput){
		this(width, cableInput, 0);
	}
	/**
	 * Moves the adapter to another mother. 
	 * @param newMother
	 */
	public void reMother(Cable newMother) {
		motherCable = newMother;
	}
	
	/**
	 * By assign will NOT reset mother cable.
	 */
	@Override
	public boolean assign(Cable cable) {
		long oldValue = motherCable.toInteger();
		super.assign(cable);
		return motherCable.toInteger() != oldValue;
	}
	
	/**
	 * Returns width.
	 */
	@Override
	public int getWidth() {
		return width;
	}
	/**
	 * Returns the bit in the mother cable.
	 */
	@Override
	public boolean getBit(int bitPos) {
		return motherCable.getBit(bitPos + offset);
	}
	/**
	 * Sets the bit in the mother cable.
	 */
	@Override
	public void putBit(int bitPos, boolean val) {
		motherCable.putBit(bitPos + offset, val);
	}
}
