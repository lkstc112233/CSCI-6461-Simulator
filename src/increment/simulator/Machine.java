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
		chips.put("MBR_input_mux", new Mux(1, 16));
		chips.put("MBR_Gate", new Gate(16));
		chips.put("PC", new ClockRegister(12));
		chips.put("PC_Gate", new Gate(12));
		chips.put("EA_Gate", new Gate(16));
		chips.put("GeneralPurposeRegisterFile", new RegisterFile(2, 16));
		chips.put("IndexRegisterFile", new RegisterFile(2, 16));
		chips.put("address_adder", new Adder(16));
		chips.put("address_adder_operand_1_mux", new Mux(1, 5));
		chips.put("GPRF_Gate", new Gate(16));
		chips.put("PC_Adder", new Adder(12));
		chips.put("Constant 1", new ConstantChip(1, 1));
		chips.put("Constant 0", new ConstantChip(1));
		// Make bus.
		cables.put("bus", new SingleCable(16));
		// Connect chips.
		singleConnect("PC", "write", "CU", "PC_write", 1);
		singleConnect("PC_Gate", "input", "PC", "output", 12);
		getChip("PC_Adder").connectInput("operand1", getChip("PC").getOutput("output"));
		getChip("PC_Adder").connectInput("operand2", new SingleCable(12));
		getChip("Constant 1").connectOutput("output", new CableAdapter(1, getChip("PC_Adder").getInput("operand2")));
		singleConnect("PC", "input", "PC_Adder", "result", 12);
		singleConnect("PC_Gate", "transfer", "CU", "PC_output", 1);
		Cable foo = new CableAdapter(12, getCable("bus"));
		getChip("PC_Gate").connectOutput("output", foo);
		foo = new CableAdapter(12, getCable("bus"));
		getChip("MAR").connectInput("input", foo);
		singleConnect("MAR", "write", "CU", "MAR_write", 1);
		singleConnect("memory", "address", "MAR", "output", 12);
		singleConnect("MBR_input_mux", "input0", "memory", "output", 16);
		getChip("MBR_input_mux").connectInput("input1", getCable("bus"));
		singleConnect("MBR_input_mux", "sel", "CU", "MBR_input_sel", 1);
		singleConnect("MBR", "input", "MBR_input_mux", "output", 16);
		singleConnect("MBR", "write", "CU", "memory_read", 1);
		singleConnect("MBR_Gate", "input", "MBR", "output", 16);
		getChip("memory").connectInput("input", getChip("MBR").getOutput("output"));
		singleConnect("memory", "write", "CU", "memory_write", 1);
		singleConnect("MBR_Gate", "transfer", "CU", "MBR_output", 1);
		getChip("MBR_Gate").connectOutput("output", getCable("bus"));
		getChip("IR").connectInput("input", getCable("bus"));
		singleConnect("decoder", "input", "IR", "output", 16);
		singleConnect("IR", "write", "CU", "IR_write", 1);
		singleConnect("CU", "opcode", "decoder", "opcode", 6);
		singleConnect("EA_Gate", "transfer", "CU", "EA_Gate", 1);
		singleConnect("GeneralPurposeRegisterFile", "address", "decoder", "R", 2);
		singleConnect("GeneralPurposeRegisterFile", "write", "CU", "GPRF_write", 1);
		getChip("GeneralPurposeRegisterFile").connectInput("input", getCable("bus"));
		singleConnect("GPRF_Gate", "input", "GeneralPurposeRegisterFile", "output", 16);
		singleConnect("GPRF_Gate", "transfer", "CU", "GPRF_output", 1);
		getChip("GPRF_Gate").connectOutput("output", getCable("bus"));
		singleConnect("IndexRegisterFile", "address", "decoder", "IX", 2);
		singleConnect("IndexRegisterFile", "write", "CU", "IRF_write", 1);
		getChip("address_adder").connectInput("operand1", new SingleCable(16));
		singleConnect("address_adder_operand_1_mux", "input0", "decoder", "address", 5);
		singleConnect("address_adder_operand_1_mux", "sel", "CU", "IRF_only", 1);
		getChip("address_adder_operand_1_mux").connectOutput("output", new CableAdapter(5, getChip("address_adder").getInput("operand1")));
		singleConnect("address_adder", "operand2", "IndexRegisterFile", "output", 16);
		getChip("EA_Gate").connectOutput("output", getCable("bus"));
		singleConnect("EA_Gate", "input", "address_adder", "result", 16);
		
		// SIMULATED Boot Loader: 
		// It loads a testing program into the memory address 0x10, and sets PC to
		// 0x10.
		((ClockRegister)getChip("PC")).setValue(0x10);
		((RegisterFile)getChip("IndexRegisterFile")).setValue(0, 0);
		mem.putValue(0x10, 0x071F); // LDR 3,0,31,0	0000 0111 0001 1111
		mem.putValue(0x11, 0x0B14); // STR 3,0,20,0	0000 1011 0001 0100
		mem.putValue(0x12, 0x0D06); // LDA 1,0,6,0	0000 1101 0000 0110
		mem.putValue(0x13, 0x0000); // HALT
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
