package increment.simulator;

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
	}
}
