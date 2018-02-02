package increment.simulator.ui;

import increment.simulator.ClockRegister;
import increment.simulator.Machine;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanLongProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanLongPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.beans.property.LongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A wrapper class for machine intended to support features from data binding.
 * @author Xu Ke
 *
 */
public class MachineWrapper {
    private IntegerProperty tickProperty;
    // Define the program counter property
    private ReadOnlyJavaBeanStringProperty programCounterProperty;
	public MachineWrapper(Machine machine) {
    	this.machine = machine;
    	try {
    		tickProperty = new SimpleIntegerProperty();
			programCounterProperty = new ReadOnlyJavaBeanStringPropertyBuilder().bean(this).name("programCounter").build();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(-1);
		}
    }
	
    public ReadOnlyJavaBeanStringProperty getProgramCounterProperty() {
		return programCounterProperty;
	}
    
    public IntegerProperty getTickProperty() {
		return tickProperty;
	}
    
    public int getTick() {
    	return tickProperty.get();
    }
    public void setTick(int value){
    	tickProperty.set(value);
    }

    private Machine machine;
    
	// Define a getter for the property's value
    public final String getProgramCounter(){return ((ClockRegister)machine.getChip("PC")).toString();}
    
    
	private boolean toTick = true;
    public void tick() {
    	if (toTick){
    		machine.tick();
    		setTick(getTick() + 1);
    	} else {
        	machine.evaluate();
    	}
    	toTick = !toTick;
    	programCounterProperty.fireValueChangedEvent();
    }
}
