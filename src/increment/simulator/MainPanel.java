package increment.simulator;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * 
 * @author Xu Ke
 *
 */
public class MainPanel extends JFrame {

	/**
	 * serial Id.
	 */
	private static final long serialVersionUID = 6519847254463238198L;
	
	private Machine machine = null;
	/**
	 * Constructor. Takes a machine to show.
	 * 
	 * @param machine
	 */
	public MainPanel(Machine machine){
		this.machine = machine;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setTitle("Machine Simulation");
		setResizable(false);
		setLayout(new GridLayout(2,1));
	}
}
