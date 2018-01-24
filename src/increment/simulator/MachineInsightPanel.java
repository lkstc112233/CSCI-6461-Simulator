package increment.simulator;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private int currentTick = 0;
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
		setLayout(new GridLayout(0, 4));
		// Add parts to window from machine;
		
		// Add a button to handle tick.
		JButton jb = new JButton();
		jb.setText("Tick");
		jb.addActionListener(new ActionListener() {
			boolean nowTick = true;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (nowTick) {
					machine.tick();
					currentTick += 1;
				} else
					machine.evaluate();
				updateUI();
				nowTick = !nowTick;
			}});
		add(jb);

		// Add a panel for Tick.
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(new JLabel("Tick"));
		JLabel foo = new JLabel("Tick Value here");
		mapping.put("tick", foo);
		jp.add(foo);
		add(jp);
		// Add a panel for PC.
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(new JLabel("PC"));
		foo = new JLabel("PC Value here");
		mapping.put("PC", foo);
		jp.add(foo);
		add(jp);
		// Add a panel for bus.
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(new JLabel("BUS"));
		foo = new JLabel("BUS");
		mapping.put("bus", foo);
		jp.add(foo);
		add(jp);
		// Add a panel for MAR.
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(new JLabel("MAR"));
		foo = new JLabel("mar");
		mapping.put("MAR", foo);
		jp.add(foo);
		add(jp);
		// Add a panel for MBR.
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(new JLabel("MBR"));
		foo = new JLabel("mbr");
		mapping.put("MBR", foo);
		jp.add(foo);
		add(jp);
		updateUI();
	}
	
	private void updateUI() {
		mapping.get("tick").setText("Tick: " + Integer.toString(currentTick));
		mapping.get("PC").setText(machine.getChip("PC").toString());
		mapping.get("bus").setText(machine.getCable("bus").toString());
		mapping.get("MAR").setText(machine.getChip("MAR").toString());
		mapping.get("MBR").setText(machine.getChip("MBR").toString());
	}
	
	private Map<String, JLabel> mapping;

}
