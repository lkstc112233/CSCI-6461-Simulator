package increment.simulator;


/**
 * A chip. This chip works as a black box. It takes 0 or more inputs (as cable), and gives
 * 0 or more outputs (also as cable). 
 * 
 * Each input or output has a name, and a specific width.
 * 
 * This Chip class works as an abstract class.
 * 
 * @author Xu Ke
 *
 */
public abstract class Chip {
	public Chip(){
		
	}
	/**
	 * We are using tick here to indicate a clock tick, so we don't have to simulate a 
	 * wiring between clock and each chip.
	 * 
	 * This method only simulates the instant tick happens. It doesn't change any of the 
	 * chip's outputs.
	 * 
	 */
	public void tick(){}
	/**
	 * Instead, this method evaluates any change in the chip (which takes time to happen).
	 * 
	 * Such as a change in the outputs.
	 */
	public void evaluate(){}
}
