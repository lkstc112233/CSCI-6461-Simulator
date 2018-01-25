package increment.simulator;
/**
 * Dummy cable. It does nothing when being put a bit and always returns 0 when being gotten.
 * @author Xu Ke
 *
 */
public class DummyCable extends Cable {
	protected int width;
	public DummyCable(int width) {
		this.width = width;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public boolean getBit(int bitPos) {
		return false;
	}

	@Override
	public void putBit(int bitPos, boolean val) {
		
	}

}
