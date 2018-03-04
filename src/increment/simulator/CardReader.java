package increment.simulator;

import java.io.IOException;
import java.io.InputStream;

/**
 * The card reader. Not implemented yet.
 * @author Xu Ke
 *
 */
public class CardReader extends IODevice {
	private InputStream card;
	private short buffer = 0;
	private boolean bufferValid = false;
	private boolean cardToEnd = false;
	@Override
	public short input() {
		if (card == null)
			return 0;
		if (bufferValid)
			return buffer;
		if (cardToEnd)
			return 0;
		// Reads two bytes to form a short.
		// Big endian, as Java.
		int higherBit = -1;
		try {
			higherBit = card.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (higherBit == -1) {
			cardToEnd = true;
			return 0;
		}
		int lowerBit = 0;
		try {
			lowerBit = card.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lowerBit == -1) {
			cardToEnd = true;
			lowerBit = 0;
		}
		buffer = (short) ((higherBit << 8) | (lowerBit));
		bufferValid = true;
		return buffer;
	}

	@Override
	public short status() {
		if (card == null)
			return 1;
		if (cardToEnd)
			return 1;
		return 0;
	}
	
	@Override
	public void tick() {
		bufferValid = false;
	}
	
	public void insertCard(InputStream card) {
		this.card = card;
		bufferValid = false;
		cardToEnd = false;
	}
}
