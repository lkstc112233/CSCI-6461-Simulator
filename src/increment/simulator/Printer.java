package increment.simulator;

import java.util.LinkedList;

/**
 * The console printer, or the screen.
 * @author Xu Ke
 *
 */
public class Printer extends IODevice {
	private LinkedList<String> console;
	
	public Printer() {
		console = new LinkedList<>();
		console.add("");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String nl = "";
		synchronized(this) {
			for (String str : console) {
				sb.append(nl);
				sb.append(str);
				nl = "\n";
			}
		}
		return sb.toString();
	}
	
	@Override
	public void output(short word) {
		synchronized(this) {
			if (word == '\n')
				console.add("");
			else {
				String last = console.removeLast();
				if (last.length() < 60) {
					last += (char) word;
					console.add(last);
				} else {
					console.add(last);
					console.add("" + (char) word);
				}
			}
			while (console.size() > 16) {
				console.removeFirst();
			}
		}
	}
}
