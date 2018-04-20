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
	private long timeSaved = 0;
	private long totalTime = 0;
	
	public Predictor() {
		addPort("EA", 16);
		addPort("PC", 12);
		addPort("opcode", 7);
	}
	
	@Override
	public void tick() {
		if (getPort("EA").toInteger() == EALast && getPort("PC").toInteger() == PCLast && getPort("PC").toInteger() == opcodeLast)
			return;
		if (getPort("PC").toInteger() != PCLast){
			predictSucceed = getPort("PC").toInteger() == predict;
			if (predictSucceed)
				timeSaved += 1;
			totalTime += 1;
		}
		EALast = getPort("EA").toInteger();
		PCLast = getPort("PC").toInteger();
		opcodeLast = (int) getPort("opcode").toInteger();
		switch(opcodeLast) {
		case 0x08: // JZ
		case 0x09: // JNE
		case 0x0A: // JCC
		case 0x0E: // SOB
		case 0x0F: // JGE
			if (EALast > PCLast)
				predict = PCLast + 1;
			else
				predict = EALast;
			break;
		case 0x0B: // JMA
		case 0x0C: // JSR
		case 0x0D: // RFS
			predict = EALast;
			break;
		default:
			predict = PCLast + 1;
			break;
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Next Instruction Prediction: ");
		sb.append(predict);
		sb.append("\n");
		sb.append("Second Next Instruction Prediction: ");
		sb.append(predict + 1);
		sb.append("\n");
		sb.append("Third Next Instruction Prediction: ");
		sb.append(predict + 2);
		sb.append("\n");
		
		sb.append("Last prediction was ");
		sb.append(predictSucceed?"Succeed.\n":"Failed.\n");
		sb.append("Predict succeed rate: ");
		if (totalTime > 0)
			sb.append(Double.toString(((double) timeSaved) * 100 / totalTime));
		else
			sb.append(0);
		sb.append("%.\n");
		
		return sb.toString();
	}
}
