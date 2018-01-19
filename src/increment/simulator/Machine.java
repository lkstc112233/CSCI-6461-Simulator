package increment.simulator;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Xu Ke
 *
 */
public class Machine {
	public Machine() {
		chips = new HashMap<>();
		cables = new HashMap<>();
		chips.put("PC", new ClockRegister(16));
	}
	private Map<String, Chip> chips;
	private Map<String, Cable> cables;
	public Chip getChip(String name) {
		return chips.get(name);
	}
	public Cable getCable(String name) {
		return cables.get(name);
	}
	public void tick(){
		for (Map.Entry<String, Chip> e : chips.entrySet()) {
			e.getValue().tick();
		}
	}
	public void evaluate(){
		for (Map.Entry<String, Chip> e : chips.entrySet()) {
			e.getValue().evaluate();
		}
	}
}
