package increment.simulator.userInterface;

import increment.simulator.ui.MachineWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;



public class MainPanelController {
	public void setMachine(MachineWrapper machine) {
		Text_PC_Text.textProperty().bind(machine.getProgramCounterProperty());
	}
	
    @FXML private Text Text_PC_Text;
    @FXML private  Text Text_PC_Label;

    public void handleTickButtonAction(ActionEvent actionEvent) {

    }

    public void handleAutoTickButtonAction(ActionEvent actionEvent) {
    }

    public void handleShowMagicButtonAction (ActionEvent actionEvent) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/ControlPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }


}
