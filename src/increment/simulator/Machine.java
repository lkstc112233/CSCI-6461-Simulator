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
		// Make chips.
		chips.put("PC", new ClockRegister(16));
		chips.put("Constant 1", new ConstantChip(1, 1));
		chips.put("Constant 0", new ConstantChip(1));
		// Connect chips.
		Cable foo = new Cable(1);
		getChip("PC").connectInput("write", foo);
		getChip("Constant 0").connectOutput("output", foo);
		
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
