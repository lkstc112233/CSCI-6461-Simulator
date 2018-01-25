package increment.simulator;

import java.util.HashMap;
import java.util.Map;

/**
 * A simulated machine.
 * @author Xu Ke
 *
 */
public class Machine {
	public Machine() {
		chips = new HashMap<>();
		cables = new HashMap<>();
		// Initialize Memory
		Memory mem;
		chips.put("memory", mem = new Memory());
		// Initialize Control Unit.
		chips.put("CU", new ControlUnit());
		// Make chips.
		chips.put("decoder", new InstructionDecoder());
		chips.put("IR", new ClockRegister(16));
		chips.put("MAR", new ClockRegister(12));
		chips.put("MBR", new ClockRegister(16));
		chips.put("MBR_Gate", new Gate(16));
		chips.put("PC", new ClockRegister(12));
		chips.put("PC_Gate", new Gate(12));
		chips.put("Direct_EA", new Gate(5));
		chips.put("Constant 1", new ConstantChip(1, 1));
		chips.put("Constant 0", new ConstantChip(1));
		// Make bus.
		cables.put("bus", new SingleCable(16));
		// Connect chips.
		singleConnect("PC", "write", "CU", "PC_write", 1);
		singleConnect("PC_Gate", "input", "PC", "output", 12);
		singleConnect("PC_Gate", "transfer", "CU", "PC_output", 1);
		Cable foo = new CableAdapter(12, getCable("bus"));
		getChip("PC_Gate").connectOutput("output", foo);
		foo = new CableAdapter(12, getCable("bus"));
		getChip("MAR").connectInput("input", foo);
		singleConnect("MAR", "write", "CU", "MAR_write", 1);
		singleConnect("memory", "address", "MAR", "output", 12);
		singleConnect("MBR", "input", "memory", "output", 16);
		singleConnect("MBR", "write", "CU", "memory_read", 1);
		singleConnect("MBR_Gate", "input", "MBR", "output", 16);
		singleConnect("MBR_Gate", "transfer", "CU", "MBR_output", 1);
		getChip("MBR_Gate").connectOutput("output", getCable("bus"));
		getChip("IR").connectInput("input", getCable("bus"));
		singleConnect("decoder", "input", "IR", "output", 16);
		singleConnect("IR", "write", "CU", "IR_write", 1);
		// SIMULATED Boot Loader: 
		// It loads a testing program into the memory address 0x10, and sets PC to
		// 0x10.
		((ClockRegister)getChip("PC")).setValue(0x10);
		mem.putValue(0x10, 0x071F); // LDR 3,0,31,0
		mem.putValue(0x11, 0x0B14); // STR 3,0,20,0
		mem.putValue(0x12, 0x0000); // HALT
		mem.putValue(0x1F, 0x1234); // Data at 0x1F
	}
	/**
	 * Connects two ports on two chips, one with an input and the other with an output,
	 * with a single cable of given width.
	 * @param inputChipName
	 * @param inputChipPort
	 * @param outputChipName
	 * @param outputChipPort
	 * @param width
	 */
	private void singleConnect(String inputChipName, String inputChipPort,String outputChipName, String outputChipPort, int width){
		Cable foo = new SingleCable(width);
		connect(inputChipName, inputChipPort, outputChipName, outputChipPort, foo);
	}
	/**
	 * Connects two ports on two chips, one with an input and the other with an output,
	 * with a given cable.
	 * @param inputChipName
	 * @param inputChipPort
	 * @param outputChipName
	 * @param outputChipPort
	 * @param cable
	 */
	private void connect(String inputChipName, String inputChipPort,String outputChipName, String outputChipPort, Cable cable) {
		getChip(inputChipName).connectInput(inputChipPort, cable);
		getChip(outputChipName).connectOutput(outputChipPort, cable);
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
		boolean change = true;
		while (change) {
			change = false;
			for (Map.Entry<String, Chip> e : chips.entrySet()) {
				if (e.getValue().evaluate())
					change = true;
			}
		}
	}
}
