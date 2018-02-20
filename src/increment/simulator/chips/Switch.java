package increment.simulator.chips;

import increment.simulator.Cable;
import increment.simulator.SingleCable;

/**
 * A simulated switch, which can be on and off. It's going to output either true or false, depending 
 * on whether it is turned to on or off.<br>
 * 
 * A switch has one output: <br>
 * 		* output[1] indicating whether it's on or off.
 * @author Xu Ke
 *
 */
public class Switch extends Chip {
	/**
	 * Switch status storage. One bit.
	 */
	protected Cable status;
	/**
	 * Constructor.
	 */
	public Switch() {
		status = new SingleCable(1);
		addPort("output", 1);
	}
	/**
	 * Filp the switch to on or off.
	 * @param stat true if on.
	 */
	public void flip(boolean stat) {
		status.putBit(0, stat);
	}
	/**
	 * Move the switch status to output.
	 */
	@Override
	public boolean evaluate() {
		return getPort("output").assign(status);
	}
}
