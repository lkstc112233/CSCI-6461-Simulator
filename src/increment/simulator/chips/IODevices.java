package increment.simulator.chips;

import increment.simulator.IODevice;

/**
 * The IO device bus interface.
 * 
 * 4 Inputs:
 * 		* write[16]
 * 		* port[5]
 * 		* load[1]
 * 		* active[1] this bit is used to manipulate buffers.
 * 1 Outputs:
 * 		* read[16]
 * @author Xu Ke
 *
 */
public class IODevices extends Chip {
	private IODevice devices[];
	public IODevices() {
		devices = new IODevice[32];
		addPort("write", 16);
		addPort("read", 16);
		addPort("port", 5);
		addPort("load", 1);
		addPort("active", 1);
	}
	@Override
	public void tick() {
		int dev = (int) getPort("port").toInteger();
		boolean load = getPort("load").getBit(0);
		boolean active = getPort("active").getBit(0);
		if (load)
			devices[dev].output((short)getPort("write").toInteger());
		if (active)
			devices[dev].tick();
	}
	
	@Override
	public boolean evaluate() {
		boolean active = getPort("active").getBit(0);
		if (active)
			return assignPort("read", devices[(int) getPort("port").toInteger()].input());
		return false;
	}
	public void connectDevice(int i, IODevice device) {
		devices[i] = device;
	}
}
