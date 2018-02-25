package increment.simulator.userInterface;

import increment.simulator.ui.MachineWrapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.util.Duration;

public class FrontPanelController {
	private MachineWrapper machine;
    Timeline automaticTick;
	@FXML private RadioButton Radio_Pause;
	@FXML private GridPane FrontPanel;
    @FXML private Slider Slider_Auto_set;

          private Duration duration;

	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
		Radio_Pause.selectedProperty().bind(this.machine.getPausedProperty());

        for(int i = 0 ; i < 12; i++)
       ((RadioButton) FrontPanel.lookup("#r" + i)).selectedProperty().bind(machine.getAddressBulbsProperty(i));

        for(int j = 0 ; j < 16 ; j++)
        ((RadioButton)FrontPanel.lookup("#s" + j)).selectedProperty().bind(machine.getValueBulbsProperty(j));

        for(int i = 0 ; i < 16; i++)
        machine.getSwitchesProperty(i).bind(((CheckBox)FrontPanel.lookup("#Check_Switch_"+i)).selectedProperty());

        automaticTick = new Timeline(new KeyFrame(Duration.seconds(1), event -> machine.tick()));


        automaticTick.setCycleCount(Timeline.INDEFINITE);



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

    public void handleAutoTickButtonAction(ActionEvent actionEvent) {
        if (Animation.Status.RUNNING == automaticTick.getStatus())
            automaticTick.stop();
        else {
            duration = Duration.seconds(Slider_Auto_set.getValue());
            KeyFrame keyFrame = new KeyFrame(duration,event -> {machine.tick();});
            automaticTick.getKeyFrames().setAll(keyFrame);

            automaticTick.play();

        }
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

    public void handleMemoryRegister(ActionEvent actionEvent) {
        machine.setRadioSwitch(2);
        machine.forceUpdate();

    }

    public void handlePCRegister(ActionEvent actionEvent) {
        machine.setRadioSwitch(0);
        machine.forceUpdate();
    }

    public void handleGPRFRegister(ActionEvent actionEvent) {
        machine.setRadioSwitch(5);
        machine.forceUpdate();
    }

    public void handleIRFRegister(ActionEvent actionEvent) {

        machine.setRadioSwitch(4);
        machine.forceUpdate();
    }

    public void handleRadioswitch0(ActionEvent actionEvent) {
        machine.setRegisterRadioSwitch(0);
        machine.forceUpdate();
    }

    public void handleRadioswitch1(ActionEvent actionEvent) {
        machine.setRegisterRadioSwitch(1);
        machine.forceUpdate();
    }

    public void handleRadioswitch2(ActionEvent actionEvent) {
        machine.setRegisterRadioSwitch(2);
        machine.forceUpdate();
    }

    public void handleRadioswitch3(ActionEvent actionEvent) {
        machine.setRegisterRadioSwitch(3);
        machine.forceUpdate();
    }





}
