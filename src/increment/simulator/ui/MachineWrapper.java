package increment.simulator.ui;

import java.util.ArrayList;
import java.util.List;

import increment.simulator.BulbSet;
import increment.simulator.Machine;
import increment.simulator.Memory;
import increment.simulator.NumberedSwitch;
import increment.simulator.Switch;
import increment.simulator.SwitchesSet;
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

	public class MachineStatusPropertyGetterOrSetter {
		int index;
		public MachineStatusPropertyGetterOrSetter(int i) {
			index = i;
		}
		public final Boolean getValueBulbs(){ return ((BulbSet)machine.getChip("panelValueBulbSet")).getBit(index);}
	    public final Boolean getAddressBulbs(){ return ((BulbSet)machine.getChip("panelAddressBulbSet")).getBit(index); }
	    public final Boolean getSwitches(){ return false; }
	    public final void setSwitches(Boolean value){ ((SwitchesSet)machine.getChip("panelSwitchSet")).flipBit(index, value); }
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
    public final String getProgramCounter(){ return machine.getChip("PC").toString(); }
    public final String getBus(){ return machine.getCable("bus").toString(); }
    public final String getMemoryAddressRegister(){ return machine.getChip("MAR").toString(); }
    public final String getMemoryBufferRegister(){ return machine.getChip("MBR").toString(); }
    public final String getInstructionRegister(){ return machine.getChip("IR").toString(); }
    public final String getGeneralPurposeRegisterFile(){ return machine.getChip("GeneralPurposeRegisterFile").toString(); }
    public final String getIndexRegisterFile(){ return machine.getChip("IndexRegisterFile").toString(); }
    public final String getMemory(){ return machine.getChip("memory").toString(); }
    public final String getControlUnit(){ return machine.getChip("CU").toString(); }
    public final Integer getRadioSwitch(){ return ((NumberedSwitch) machine.getChip("panelDestSelectSwitch")).getValue(); }
    public final void setRadioSwitch(Integer value){ ((NumberedSwitch) machine.getChip("panelDestSelectSwitch")).setValue(value); }
    public final Integer getRegisterRadioSwitch(){ return ((NumberedSwitch) machine.getChip("panelRegisterSelectionSwitch")).getValue(); }
	public final void setRegisterRadioSwitch(Integer value) { ((NumberedSwitch) machine.getChip("panelRegisterSelectionSwitch")).setValue(value); }
	public final Boolean getPaused(){ return machine.getCable("paused").getBit(0); }
	   
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
		((Switch) machine.getChip("panelResetCUSwitch")).flip(true);
		forceTick();
		((Switch) machine.getChip("panelResetCUSwitch")).flip(false);
	}
	public void forceLoadMAR() {
		loadSomething(1);
	}
	public void forceLoad() {
		// ((Switch) machine.getChip("panelPauseCUSwitch")).flip(true);
		((Switch) machine.getChip("panelLoadSwitch")).flip(true);
		forceTick();
		// ((Switch) machine.getChip("panelPauseCUSwitch")).flip(paused);
		((Switch) machine.getChip("panelLoadSwitch")).flip(false);
		forceUpdate();
	}
	private void loadSomething(int id) {
		// ((Switch) machine.getChip("panelPauseCUSwitch")).flip(true);
		((Switch) machine.getChip("panelLoadSwitch")).flip(true);
		// TODO: Move out onto panel in next stage.
		int oldValue = getRadioSwitch();
		setRadioSwitch(id);
		forceTick();
		// ((Switch) machine.getChip("panelPauseCUSwitch")).flip(paused);
		((Switch) machine.getChip("panelLoadSwitch")).flip(false);
		setRadioSwitch(oldValue);
		forceUpdate();
	}
	private boolean paused = false;
	public void pauseOrRestore() {
		paused = !paused;
		((Switch) machine.getChip("panelPauseCUSwitch")).flip(paused);
		forceUpdate();
	}
	public void IPLButton() {
		machine.IPLMagic();
    	updateEvent();
	}
}
