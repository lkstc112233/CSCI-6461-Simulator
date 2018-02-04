package increment.simulator;
/**
 * A cable partial adapter. It works like a cable adapter but it only affects the
 * bits it's linked in.
 * @author Xu Ke
 *
 */
public class CablePartialAdapter extends Cable {
	int width;
	int offset;
	Cable motherCable;
	public CablePartialAdapter(int width, Cable cableInput, int offset) {
		this.width = width;
		this.offset = offset;
		this.motherCable = cableInput;
	} 
	
	public CablePartialAdapter(int width, Cable cableInput){
		this(width, cableInput, 0);
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
	 * Returns the bit.
	 */
	@Override
	public boolean getBit(int bitPos) {
		return motherCable.getBit(bitPos + offset);
	}
	/**
	 * Sets the bit.
	 */
	@Override
	public void putBit(int bitPos, boolean val) {
		motherCable.putBit(bitPos + offset, val);
	}

}
