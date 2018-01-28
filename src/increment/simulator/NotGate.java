package increment.simulator;
/**
 * A not gate. It reverses every bit in input and move it to output.
 * 
 * A not gate has one input:
 * 		* input[width]
 * A not gate has one output:
 * 		* output[width]
 * 
 * @author Xu Ke
 *
 */
public class NotGate extends Chip {
	public NotGate(int width){
		addPort("input", width);
		addPort("output", width);
	}
	
	@Override
	public boolean evaluate() {
		return getPort("output").assignReverse(getPort("input"));
	}
}
