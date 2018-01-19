package increment.simulator;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;
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
		mapping = new HashMap<>();
		setSize(900, 600);
		setTitle("Machine Inside");
		setResizable(false);
		setLayout(new GridLayout(4,4));
		// Add parts to window from machine;
		
	}
	
	private Map<String, JTextField> mapping;

}
