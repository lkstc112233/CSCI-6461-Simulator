package increment.simulator.userInterface;

import increment.simulator.ui.MachineWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontPanelController {
	private MachineWrapper machine;
	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
	}

    public void handleCircleButtonAction(ActionEvent actionEvent) {
    }

    public void handlePauseButtonAction(ActionEvent actionEvent) {
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
