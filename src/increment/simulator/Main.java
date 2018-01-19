package increment.simulator;

public class Main {

	public static void main(String[] args) {
		Machine mainMachine = new Machine();
		MainPanel mp = new MainPanel(mainMachine);
		MachineInsightPanel mip = new MachineInsightPanel(mainMachine);
		mp.setVisible(true);
		mip.setVisible(true);
	}

}
