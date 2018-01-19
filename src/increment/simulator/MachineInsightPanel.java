package increment.simulator;

import java.awt.GridLayout;

import javax.swing.JFrame;
/**
 * 
 * @author Xu Ke
 *
 */
public class MachineInsightPanel extends JFrame {

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = -5382594678405707577L;
	private Machine machine = null;
	/**
	 * Constructor. Takes a machine to show.
	 * 
	 * @param machine
	 */
	public MachineInsightPanel(Machine machine){
		this.machine = machine;
		setSize(900,600);
		setTitle("Machine Inside");
		setResizable(false);
		setLayout(new GridLayout(4,4));
	}

}
