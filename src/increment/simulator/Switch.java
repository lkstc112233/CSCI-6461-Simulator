package increment.simulator;

/**
 * A simulated switch, which can be on and off. It's going to output either true or false, depending 
 * on whether it is turned to on or off.
 * 
 * A switch has one output: <br>
 * 		* output[1] indicating whether it's on or off.
 * @author Xu Ke
 *
 */
public class Switch extends Chip {
	protected Cable status;
	public Switch() {
		status = new SingleCable(1);
		addPort("output", 1);
	}
	
	public void flip(boolean stat) {
		status.putBit(0, stat);
	}
	
	@Override
	public boolean evaluate() {
		return getPort("output").assign(status);
	}
}
