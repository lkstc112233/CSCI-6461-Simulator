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
    @FXML private RadioButton r0;
    @FXML private RadioButton r1;
    @FXML private RadioButton r2;
    @FXML private RadioButton r3;
    @FXML private RadioButton r4;
    @FXML private RadioButton r5;
    @FXML private RadioButton r6;
    @FXML private RadioButton r7;
    @FXML private RadioButton r8;
    @FXML private RadioButton r9;
    @FXML private RadioButton r10;
    @FXML private RadioButton r11;

    @FXML private RadioButton s0;
    @FXML private RadioButton s1;
    @FXML private RadioButton s2;
    @FXML private RadioButton s3;
    @FXML private RadioButton s4;
    @FXML private RadioButton s5;
    @FXML private RadioButton s6;
    @FXML private RadioButton s7;
    @FXML private RadioButton s8;
    @FXML private RadioButton s9;
    @FXML private RadioButton s10;
    @FXML private RadioButton s11;
    @FXML private RadioButton s12;
    @FXML private RadioButton s13;
    @FXML private RadioButton s14;
    @FXML private RadioButton s15;





	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
		Radio_Pause.selectedProperty().bind(this.machine.getPausedProperty());
        r0.selectedProperty().bind(machine.getAddressBulbsProperty(0));
        r1.selectedProperty().bind(machine.getAddressBulbsProperty(1));
        r2.selectedProperty().bind(machine.getAddressBulbsProperty(2));
        r3.selectedProperty().bind(machine.getAddressBulbsProperty(3));
        r4.selectedProperty().bind(machine.getAddressBulbsProperty(4));
        r5.selectedProperty().bind(machine.getAddressBulbsProperty(5));
        r6.selectedProperty().bind(machine.getAddressBulbsProperty(6));
        r7.selectedProperty().bind(machine.getAddressBulbsProperty(7));
        r8.selectedProperty().bind(machine.getAddressBulbsProperty(8));
        r9.selectedProperty().bind(machine.getAddressBulbsProperty(9));
        r10.selectedProperty().bind(machine.getAddressBulbsProperty(10));
        r11.selectedProperty().bind(machine.getAddressBulbsProperty(11));

        s0.selectedProperty().bind(machine.getValueBulbsProperty(0));
        s1.selectedProperty().bind(machine.getValueBulbsProperty(1));
        s2.selectedProperty().bind(machine.getValueBulbsProperty(2));
        s3.selectedProperty().bind(machine.getValueBulbsProperty(3));
        s4.selectedProperty().bind(machine.getValueBulbsProperty(4));
        s5.selectedProperty().bind(machine.getValueBulbsProperty(5));
        s6.selectedProperty().bind(machine.getValueBulbsProperty(6));
        s7.selectedProperty().bind(machine.getValueBulbsProperty(7));
        s8.selectedProperty().bind(machine.getValueBulbsProperty(8));
        s9.selectedProperty().bind(machine.getValueBulbsProperty(9));
        s10.selectedProperty().bind(machine.getValueBulbsProperty(10));
        s11.selectedProperty().bind(machine.getValueBulbsProperty(11));
        s12.selectedProperty().bind(machine.getValueBulbsProperty(12));
        s13.selectedProperty().bind(machine.getValueBulbsProperty(13));
        s14.selectedProperty().bind(machine.getValueBulbsProperty(14));
        s15.selectedProperty().bind(machine.getValueBulbsProperty(15));





	}

    public void handleCircleButtonAction(ActionEvent actionEvent) {
        machine.forceTick();
    }

    public void handlePauseButtonAction(ActionEvent actionEvent) {
        machine.pauseOrRestore();
    }

    public void handleResetCUButtonAction(ActionEvent actionEvent) {
        machine.resetCUStatus();
    }

    public void handleIPLButtonAction(ActionEvent actionEvent) {
        machine.IPLButton();
    }

    public void handleLoadButtonAction(ActionEvent actionEvent) {
        machine.forceLoad();
    }

    public void handleLoadMARButtonAction(ActionEvent actionEvent) {
        machine.forceLoadMAR();
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
        machine.forceUpdate();
    }
}
