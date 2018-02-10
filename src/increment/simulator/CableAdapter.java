package increment.simulator;
/**
 * A cable adapter. It transfers signal from an existing cable, and puts signal to it.
 * @author Xu Ke
 *
 */
public class CableAdapter extends CablePartialAdapter {
	/**
	 * Constructor.
	 * @param width - Bit width.
	 * @param cableInput - Mother cable
	 * @param offset - default 0.
	 */
	public CableAdapter(int width, Cable cableInput, int offset) {
		super(width, cableInput, offset);
	} 
	/**
	 * Constructor. Assigning offset to 0.
	 * @param width - Bit width.
	 * @param cableInput - Mother cable
	 */
	public CableAdapter(int width, Cable cableInput){
		super(width, cableInput, 0);
	}
	
	/**
	 * By assign WILL reset mother cable.
	 */
	@Override
	public boolean assign(Cable cable) {
		long oldValue = motherCable.toInteger();
		motherCable.setZero();
		super.assign(cable);
		return motherCable.toInteger() != oldValue;
	}
}
