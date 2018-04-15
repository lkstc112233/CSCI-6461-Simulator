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
	private int opcodeLast = -1;
	
	private long predict = 0;
	private boolean predictSucceed = false;
	
	public Predictor() {
		addPort("EA", 16);
		addPort("PC", 12);
		addPort("opcode", 7);
	}
	
	@Override
	public void tick() {
		if (getPort("EA").toInteger() == EALast && getPort("PC").toInteger() == PCLast && getPort("PC").toInteger() == opcodeLast)
			return;
		if (getPort("PC").toInteger() != PCLast)
			predictSucceed = getPort("PC").toInteger() == predict;
		EALast = getPort("EA").toInteger();
		PCLast = getPort("PC").toInteger();
		opcodeLast = (int) getPort("opcode").toInteger();
		switch(opcodeLast) {
		default:
			predict = getPort("PC").toInteger() + 1;
		}
	}
	
	@Override
	public String toString(){
		return "";
	}
}
