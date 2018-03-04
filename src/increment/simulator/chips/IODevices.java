package increment.simulator.chips;

import increment.simulator.DummyIODevice;
import increment.simulator.IODevice;

/**
 * The IO device bus interface.
 * 
 * 4 Inputs:
 * 		* write[16]
 * 		* port[5]
 * 		* status[16]
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
		for (int i = 0; i < 32; ++i) {
			devices[i] = new DummyIODevice();
		}
		addPort("write", 16);
		addPort("read", 16);
		addPort("port", 5);
		addPort("load", 1);
		addPort("active", 1);
		addPort("status", 16);
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
		boolean status = false;
		if (active)
			status |= assignPort("read", devices[(int) getPort("port").toInteger()].input());
		status |= assignPort("status", devices[(int) getPort("port").toInteger()].status());
		return status;
	}
	
	public void connectDevice(int i, IODevice device) {
		devices[i] = device;
	}
}
