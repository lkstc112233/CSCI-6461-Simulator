package increment.simulator.userInterface;

import increment.simulator.ui.MachineWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.awt.*;

public class FrontPanelController {
	private MachineWrapper machine;
	@FXML private RadioButton Radio_Pause;

	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
        Radio_Pause.selectedProperty().bind(this.machine.getPausedProperty());
	}

    public void handleCircleButtonAction(ActionEvent actionEvent) {
        machine.forceTick();
    }

    public void handlePauseButtonAction(ActionEvent actionEvent) {
        machine.pauseOrRestore();
    }

    public void handleResetCUButtonAction(ActionEvent actionEvent) {
    }

    public void handleIPLButtonAction(ActionEvent actionEvent) {
    }

    public void handleLoadButtonAction(ActionEvent actionEvent) {
    }

    public void handleLoadMARButtonAction(ActionEvent actionEvent) {
    }


    public void handleDebugButtonAction(ActionEvent actionEvent) throws Exception {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/DebugPanel.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<DebugPanelController>getController().setMachine(machine);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        machine.forceUpdate();
    }

    public void handleMagicButtonAction(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/ControlPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
