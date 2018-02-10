package increment.simulator;
/**
 * A bit. A bit is the most elemental unit of the simulated system. A bit can be put or get.
 * A bit holds a value of 0 or 1.
 * <br>
 * <I>However the whole Bit structure can be replaced by {@link Boolean}...</I>
 * 
 * @author Xu Ke
 *
 */
public class Bit {
	/**
	 * interval value. Since a bit can only hold 0 or 1, there is really no point making it
	 * a integer. Further more, since we don't care it's initial value, making it with an 
	 * initial value of false is only good for syntax.
	 */
	private boolean value = false;
	/**
	 * 
	 * @return value of the bit
	 */
	public boolean get() {
		return value;
	}
	/**
	 * 
	 * @param v : value to put
	 */
	public void put(boolean v){
		value = v;
	}
}
