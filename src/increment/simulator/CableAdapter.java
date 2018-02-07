package increment.simulator;
/**
 * A cable adapter. It transfers signal from an existing cable, and puts signal to it.
 * @author Xu Ke
 *
 */
public class CableAdapter extends CablePartialAdapter {
	public CableAdapter(int width, Cable cableInput, int offset) {
		super(width, cableInput, offset);
	} 
	
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
