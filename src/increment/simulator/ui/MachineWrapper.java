package increment.simulator.ui;

import java.util.ArrayList;
import java.util.List;

import increment.simulator.Machine;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
}
