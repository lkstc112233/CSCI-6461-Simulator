package increment.simulator;
/**
 * Dummy cable. It does nothing when being put a bit and always returns 0 when being gotten.
 * @author Xu Ke
 *
 */
public class DummyCable extends Cable {
	/**
	 * Contains width of the cable.
	 */
	protected int width;
	/**
	 * Constructor.
	 * @param width
	 */
	public DummyCable(int width) {
		this.width = width;
	}
	/**
	 * Returns width.
	 */
	@Override
	public int getWidth() {
		return width;
	}
	/**
	 * Returns false always.
	 */
	@Override
	public boolean getBit(int bitPos) {
		return false;
	}
	/**
	 * Does nothing.
	 */
	@Override
	public void putBit(int bitPos, boolean val) {
		
	}

}
