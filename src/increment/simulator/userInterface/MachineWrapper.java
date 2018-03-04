package increment.simulator.userInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import increment.simulator.Machine;
import increment.simulator.chips.BulbSet;
import increment.simulator.chips.Memory;
import increment.simulator.chips.NumberedSwitch;
import increment.simulator.chips.Switch;
import increment.simulator.chips.SwitchesSet;
import increment.simulator.tools.AssemblyCompiler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;

/**
 * A wrapper class for machine intended to support features from data binding.
 * @author Xu Ke
 *
 */
public class MachineWrapper {
    private IntegerProperty tickProperty;
    public IntegerProperty getTickProperty() { return tickProperty; }
    public int getTick() { return tickProperty.get(); }
    public void setTick(int value){ tickProperty.set(value); }
    // Define the properties for wrappers.
    private ReadOnlyJavaBeanStringProperty programCounterProperty;
    public ReadOnlyJavaBeanStringProperty getProgramCounterProperty() { return programCounterProperty; }
    private ReadOnlyJavaBeanStringProperty busProperty;
	public ReadOnlyJavaBeanStringProperty getBusProperty() { return busProperty; }
	private ReadOnlyJavaBeanStringProperty memoryAddressRegisterProperty;
	public ReadOnlyJavaBeanStringProperty getMemoryAddressRegisterProperty() { return memoryAddressRegisterProperty; }
    private ReadOnlyJavaBeanStringProperty memoryBufferRegisterProperty;
	public ReadOnlyJavaBeanStringProperty getMemoryBufferRegisterProperty() { return memoryBufferRegisterProperty; }
	private ReadOnlyJavaBeanStringProperty instructionRegisterProperty;
	public ReadOnlyJavaBeanStringProperty getInstructionRegisterProperty() { return instructionRegisterProperty; }
	private ReadOnlyJavaBeanStringProperty generalPurposeRegisterFileProperty;
	public ReadOnlyJavaBeanStringProperty getGeneralPurposeRegisterFileProperty() { return generalPurposeRegisterFileProperty; }
    private ReadOnlyJavaBeanStringProperty indexRegisterFileProperty;
	public ReadOnlyJavaBeanStringProperty getIndexRegisterFileProperty() { return indexRegisterFileProperty; }
	private ReadOnlyJavaBeanStringProperty memoryProperty;
	public ReadOnlyJavaBeanStringProperty getMemoryProperty() { return memoryProperty; }
	private ReadOnlyJavaBeanStringProperty controlUnitProperty;
	public ReadOnlyJavaBeanStringProperty getControlUnitProperty() { return controlUnitProperty; }
	private ReadOnlyJavaBeanBooleanProperty[] valueBulbsProperty;
	public ReadOnlyJavaBeanBooleanProperty getValueBulbsProperty(int i) { return valueBulbsProperty[i]; }
	private ReadOnlyJavaBeanBooleanProperty[] addressBulbsProperty;
	public ReadOnlyJavaBeanBooleanProperty getAddressBulbsProperty(int i) { return addressBulbsProperty[i]; }
	private JavaBeanBooleanProperty[] switchesProperty;
	public JavaBeanBooleanProperty getSwitchesProperty(int i) { return switchesProperty[i]; }
	private JavaBeanIntegerProperty radioSwitchProperty;
	public JavaBeanIntegerProperty getRadioSwitchProperty() { return radioSwitchProperty; }
	private JavaBeanIntegerProperty registerRadioSwitchProperty;
	public JavaBeanIntegerProperty getRegisterRadioSwitchProperty() { return registerRadioSwitchProperty; }
	private ReadOnlyJavaBeanBooleanProperty pausedProperty;
	public ReadOnlyJavaBeanBooleanProperty getPausedProperty() { return pausedProperty; }
	private ReadOnlyJavaBeanStringProperty screenProperty;
	public ReadOnlyJavaBeanStringProperty getScreenProperty() { return screenProperty; }

	public class MachineStatusPropertyGetterOrSetter {
		int index;
		public MachineStatusPropertyGetterOrSetter(int i) {
			index = i;
		}
		public final Boolean getValueBulbs(){ try{return ((BulbSet)machine.getChip("panelValue")).getBit(index);}catch(NullPointerException e){return false;} }
	    public final Boolean getAddressBulbs(){ try{return ((BulbSet)machine.getChip("panelAddress")).getBit(index);}catch(NullPointerException e){return false;} }
	    public final Boolean getSwitches(){ try{return false;}catch(NullPointerException e){return false;} }
	    public final void setSwitches(Boolean value){ try{((SwitchesSet)machine.getChip("panelSwitchSet")).flipBit(index, value);}catch(NullPointerException e){} }
	}
	
	public MachineWrapper(Machine machine) {
    	this.machine = machine;
    	properties = new ArrayList<>();
    	try {
    		tickProperty = new SimpleIntegerProperty();
    		properties.add(programCounterProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("programCounter").build());
    		properties.add(busProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("bus").build());
    		properties.add(memoryAddressRegisterProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("memoryAddressRegister").build());
    		properties.add(memoryBufferRegisterProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("memoryBufferRegister").build());
    		properties.add(instructionRegisterProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("instructionRegister").build());
    		properties.add(generalPurposeRegisterFileProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("generalPurposeRegisterFile").build());
    		properties.add(indexRegisterFileProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("indexRegisterFile").build());
    		properties.add(memoryProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("memory").build());
    		properties.add(controlUnitProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("controlUnit").build());
    		properties.add(radioSwitchProperty = new JavaBeanIntegerPropertyBuilder().bean(this).name("radioSwitch").build());
    		properties.add(registerRadioSwitchProperty = new JavaBeanIntegerPropertyBuilder().bean(this).name("registerRadioSwitch").build());
    		properties.add(pausedProperty = new ReadOnlyJavaBeanBooleanPropertyBuilder().bean(this).name("paused").build());
    		properties.add(screenProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("screen").build());
    		valueBulbsProperty = new ReadOnlyJavaBeanBooleanProperty[16];
    		addressBulbsProperty = new ReadOnlyJavaBeanBooleanProperty[16];
    		switchesProperty = new JavaBeanBooleanProperty[16];
    		for (int i = 0; i < 16; ++i) {
	    		properties.add(valueBulbsProperty[i] = new ReadOnlyJavaBeanBooleanPropertyBuilder().bean(new MachineStatusPropertyGetterOrSetter(i)).name("valueBulbs").build());
	    		properties.add(addressBulbsProperty[i] = new ReadOnlyJavaBeanBooleanPropertyBuilder().bean(new MachineStatusPropertyGetterOrSetter(i)).name("addressBulbs").build());
	    		switchesProperty[i] = new JavaBeanBooleanPropertyBuilder().bean(new MachineStatusPropertyGetterOrSetter(i)).name("switches").build();
    		}
    	} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(-1);
		}
    }
	
    private Machine machine;
    
	// Define a getter for the property's value
    public final String getProgramCounter(){ try{return machine.getChip("PC").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getBus(){ try{return machine.getCable("bus").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getMemoryAddressRegister(){ try{return machine.getChip("MAR").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getMemoryBufferRegister(){ try{return machine.getChip("MBR").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getInstructionRegister(){ try{return machine.getChip("IR").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getGeneralPurposeRegisterFile(){ try{return machine.getChip("GPRF").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getIndexRegisterFile(){ try{return machine.getChip("IRF").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getMemory(){ try{return machine.getChip("memory").toString();}catch(NullPointerException e){return "Not Found";} }
    public final String getControlUnit(){ try{return machine.getChip("CU").toString();}catch(NullPointerException e){return "Not Found";} }
    public final Integer getRadioSwitch(){ try{return ((NumberedSwitch) machine.getChip("panelDestSelectSwitch")).getValue();}catch(NullPointerException e){return 0;} }
    public final void setRadioSwitch(Integer value){ try{((NumberedSwitch) machine.getChip("panelDestSelectSwitch")).setValue(value);}catch(NullPointerException e){} }
    public final Integer getRegisterRadioSwitch(){ try{return ((NumberedSwitch) machine.getChip("panelRegSelSwitch")).getValue();}catch(NullPointerException e){return 0;}  }
	public final void setRegisterRadioSwitch(Integer value) { try{((NumberedSwitch) machine.getChip("panelRegSelSwitch")).setValue(value);}catch(NullPointerException e){} }
	public final Boolean getPaused(){ try{return machine.getCable("paused").getBit(0);}catch(NullPointerException e){return false;} }
	public final String getScreen(){ try{return machine.getScreen();}catch(NullPointerException e){return "Not Found";} }
    
	private List<ReadOnlyJavaBeanProperty<?>> properties;
    
	private boolean toTick = true;
	private void updateEvent() {
		for (ReadOnlyJavaBeanProperty<?> p: properties)
			p.fireValueChangedEvent();
	}
    public void tick() {
    	if (toTick){
    		machine.tick();
    		setTick(getTick() + 1);
    	} else {
        	machine.evaluate();
    	}
    	toTick = !toTick;
    	updateEvent();
    }
    public void forceTick() {
    	machine.evaluate();
    	machine.tick();
    	toTick = false;
    	setTick(getTick() + 1);
    	updateEvent();
    }
    public void forceUpdate() {
    	machine.evaluate();
    	updateEvent();
    }
	public void putProgram(String address, String program) throws IllegalStateException, NumberFormatException{
		int intAddress = Integer.decode(address);
		((Memory)machine.getChip("memory")).loadProgram(intAddress, AssemblyCompiler.compile(program));
    	updateEvent();
	}
	public void resetCUStatus() {
		((Switch) machine.getChip("panelResetCU")).flip(true);
		forceTick();
		((Switch) machine.getChip("panelResetCU")).flip(false);
	}
	public void forceLoadMAR() {
		loadSomething(1);
	}
	public void forceLoad() {
		((Switch) machine.getChip("panelLoadSwitch")).flip(true);
		forceTick();
		((Switch) machine.getChip("panelLoadSwitch")).flip(false);
		forceUpdate();
	}
	private void loadSomething(int id) {
		((Switch) machine.getChip("panelLoadSwitch")).flip(true);
		int oldValue = getRadioSwitch();
		setRadioSwitch(id);
		forceTick();
		((Switch) machine.getChip("panelLoadSwitch")).flip(false);
		setRadioSwitch(oldValue);
		forceUpdate();
	}
	private boolean paused = false;
	public void pauseOrRestore() {
		paused = !paused;
		((Switch) machine.getChip("panelPauseCU")).flip(paused);
		forceUpdate();
	}
	public void IPLButton() {
		machine.IPLMagic();
    	updateEvent();
	}
	
	public void keyPress(short key) {
		machine.keyPress(key);
	}
	
	public void insertCard(File card) {
		try {
			machine.insertCard(new FileInputStream(card));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
