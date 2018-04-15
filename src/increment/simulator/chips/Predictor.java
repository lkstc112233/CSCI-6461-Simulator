package increment.simulator.chips;
/**
 * A simple predictor.
 * 
 * It takes information needed to perform a prediction.
 * 
 * The Predictor takes serveral inputs:
 * 		* EA[16]
 * 		* PC[12]
 * 		* opcode[7]
 * 
 * @author Xu Ke
 *
 */
public class Predictor extends Chip {
	private long EALast = -1;
	private long PCLast = -1;
	private long opcodeLast = -1;
	public Predictor() {
		addPort("EA", 16);
		addPort("PC", 12);
		addPort("opcode", 7);
	}
	
	@Override
	public void tick() {
		if (getPort("EA").toInteger() == EALast && getPort("PC").toInteger() == PCLast && getPort("PC").toInteger() == opcodeLast)
			return;
		
	}
}
