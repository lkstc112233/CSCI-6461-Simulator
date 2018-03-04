package increment.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The console keyboard.
 * @author Xu Ke
 *
 */
public class Keyboard extends IODevice {
	private Queue<Short> buffer;
	
	public Keyboard() {
		buffer = new LinkedList<>();
	}
	@Override
	public short input() {
		synchronized(this) {
			if (buffer.isEmpty())
				return 0;
			return buffer.element();
		}
	}
	
	public void pressKey(short key) {
		synchronized(this) {
			buffer.add(key);
		}
	}
	
	@Override
	public void tick() {
		synchronized(this) {
			if (!buffer.isEmpty())
				buffer.remove();
		}
	}
}
