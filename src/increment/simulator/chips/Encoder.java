package increment.simulator.chips;
/**
 * An encoder. Encodes serveral inputs into one.
 * @author Xu Ke
 *
 */
public class Encoder extends Chip{
	public Encoder(int width) {
		for (int i = 0; i < (1 << width); ++i)
			addPort("input" + i, 1);
		addPort("output", width);
	}
	
	@Override
	public boolean evaluate() {
		for (int i = 0; i < (1 << getPort("output").getWidth()); ++i)
			if (getPort("input" + i).getBit(0))
				return assignPort("output", i);
		return assignPort("output", 0);	
	}
}
