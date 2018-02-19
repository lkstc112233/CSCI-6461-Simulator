package increment.simulator.chips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import increment.simulator.Cable;

/**
 * Sometimes we need to combine serveral chips.
 * 
 * This is when we need - 
 * 
 * A chip set!
 * 
 * @author Xu Ke
 *
 */
public abstract class ChipsSet extends Chip {
	protected List<Chip> chips;
	protected Map<String, List<Object[]>> relation;
	public ChipsSet() {
		chips = new ArrayList<>();
		relation = new HashMap<>();
	}
	
	protected void addChip(Chip c) {
		chips.add(c);
	}
	
	protected void addChipPortRelation(String portOnChipSet, Chip chip, String portOnInnerChip) {
		if (!relation.containsKey(portOnChipSet)) {
			relation.put(portOnChipSet, new ArrayList<>()); 
		} 
		relation.get(portOnChipSet).add(new Object[]{chip, portOnInnerChip});
	}
	
	@Override
	public void connectPort(String name, Cable cable) {
		if (!relation.containsKey(name)) {
			super.connectPort(name, cable);
		} else {
			for (Object[] pair : relation.get(name)) {
				((Chip)pair[0]).connectPort((String)pair[1], cable);
			}
		}
	}
	
	@Override
	public int getPortWidth(String name){
		if (!relation.containsKey(name)) {
			return super.getPortWidth(name);
		} else {
			Object[] pair = relation.get(name).get(0);
			return ((Chip)pair[0]).getPortWidth((String)pair[1]);
		}
	}
	
	@Override
	public void tick() {
		for (Chip c : chips)
			c.tick();
	}
	
	@Override
	public boolean evaluate() {
		boolean result = false;
		for (Chip c : chips)
			result |= c.evaluate();
		return result;
	}
}
