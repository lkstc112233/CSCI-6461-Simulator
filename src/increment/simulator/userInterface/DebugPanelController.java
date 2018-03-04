package increment.simulator.userInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;



public class DebugPanelController {
	public void setMachine(MachineWrapper machine) {

		Text_Tick_Text.textProperty().bind(machine.getTickProperty().asString());
        Text_PC_Text.textProperty().bind(machine.getProgramCounterProperty());
		Text_BUS_Text.textProperty().bind(machine.getBusProperty());
		Text_MAR_Text.textProperty().bind(machine.getMemoryAddressRegisterProperty());
		Text_MBR_Text.textProperty().bind(machine.getMemoryBufferRegisterProperty());
		Text_IR_Text.textProperty().bind(machine.getInstructionRegisterProperty());
		Text_GPRF_Text.textProperty().bind(machine.getGeneralPurposeRegisterFileProperty());
		Text_IRF_Text.textProperty().bind(machine.getIndexRegisterFileProperty());
		Text_CU_Text.textProperty().bind(machine.getControlUnitProperty());
		Text_Memory_Text.textProperty().bind(machine.getMemoryProperty());

	}

    @FXML private  Text Text_Tick_Text;
    @FXML private  Text Text_PC_Text;
    @FXML private  Text Text_BUS_Text;
    @FXML private  Text Text_MAR_Text;
    @FXML private  Text Text_MBR_Text;
    @FXML private  Text Text_IR_Text;
    @FXML private  Text Text_GPRF_Text;
    @FXML private  Text Text_IRF_Text;
    @FXML private  Text Text_CU_Text;
    @FXML private  Text Text_Memory_Text;

    public void handleTickButtonAction(ActionEvent actionEvent) {


    }


}
