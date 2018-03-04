package increment.simulator.userInterface;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ControlPanelController {


    public Button Button_Load_Program;

    @FXML private TextArea Input_Begin_Address_At;
    @FXML private TextArea Input_Program;
    
    private MachineWrapper machine;

    @FXML
    protected void handleLoadButtonAction(ActionEvent event) {
		machine.putProgram(Input_Begin_Address_At.getText(), Input_Program.getText());
    }

	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
	}

}
