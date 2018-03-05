package increment.simulator.userInterface;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FrontPanelController   {
	private static MachineWrapper machine;
    Timeline automaticTick;
	@FXML private RadioButton Radio_Pause;
	@FXML private GridPane FrontPanel;
    @FXML private Slider Slider_Auto_set;
    @FXML private GridPane KeyboardPanel;
    


          private Duration duration;
          private Boolean Cap_look=true;

          public EventHandler handleLoadKeyboardAction;

    @FXML
    public void keyHandler(KeyEvent keyEvent){





    }




	public void setMachine(MachineWrapper machine) {
		this.machine = machine;
		Radio_Pause.selectedProperty().bind(this.machine.getPausedProperty());
      // FrontPanel.setGridLinesVisible(true);
        for(int i = 0 ; i < 12; i++)
       ((RadioButton) FrontPanel.lookup("#r" + i)).selectedProperty().bind(machine.getAddressBulbsProperty(i));

        for(int j = 0 ; j < 16 ; j++)
        ((RadioButton)FrontPanel.lookup("#s" + j)).selectedProperty().bind(machine.getValueBulbsProperty(j));

        for(int i = 0 ; i < 16; i++)
        machine.getSwitchesProperty(i).bind(((CheckBox)FrontPanel.lookup("#Check_Switch_"+i)).selectedProperty());

        automaticTick = new Timeline(new KeyFrame(Duration.seconds(1), event -> machine.tick()));

        ((Text)FrontPanel.lookup("#Text_CodeOutPut")).textProperty().bind(machine.getScreenProperty());
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

    public void handleInsertCardButtonAction(ActionEvent actionEvent) {
    	FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(FrontPanel.getScene().getWindow());
        if (file != null) {
            machine.insertCard(file);
        }
    }

    public void handleAutoTickButtonAction(ActionEvent actionEvent) {
        if (Animation.Status.RUNNING == automaticTick.getStatus()) {
            automaticTick.stop();
            Slider_Auto_set.setDisable(false);
        } else {
        	if (Slider_Auto_set.getValue() == 0)
                duration = Duration.millis(1);        		
        	else
                duration = Duration.seconds(Slider_Auto_set.getValue());
            Slider_Auto_set.setDisable(true);
            KeyFrame keyFrame = new KeyFrame(duration,event -> {machine.tick();});
            automaticTick.getKeyFrames().setAll(keyFrame);

            automaticTick.play();

        }
    }


    
    private Stage debugStage;
    public void handleDebugButtonAction(ActionEvent actionEvent) throws Exception {
    	if (debugStage == null) {
	    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/DebugPanel.fxml"));
	        Parent root = fxmlLoader.load();
	        fxmlLoader.<DebugPanelController>getController().setMachine(machine);
	        debugStage = new Stage();
	        debugStage.setScene(new Scene(root, 800, 600));
	        debugStage.setTitle("Field Engineer Console");
	        debugStage.show();
	        machine.forceUpdate();
    	} else if (!debugStage.isShowing())
    		debugStage.show();
    }

    private Stage magicStage;
    public void handleMagicButtonAction(ActionEvent actionEvent) throws Exception{
    	if (magicStage == null) {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/ControlPanel.fxml"));
	        Parent root = fxmlLoader.load();
	        fxmlLoader.<ControlPanelController>getController().setMachine(machine);
	        magicStage = new Stage();
	        magicStage.setScene(new Scene(root, 800, 600));
	        magicStage.setTitle("MAGIC Panel");
	        magicStage.show();
	        machine.forceUpdate();
    	} else if (!magicStage.isShowing())
    		magicStage.show();
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


    public void handleLoadKeyboardAction(ActionEvent actionEvent) throws Exception {

         if (KeyboardPanel.isVisible())
             KeyboardPanel.setVisible(false);
         else
            KeyboardPanel.setVisible(true);



    }


    public void keyPressedHandlerAction(ActionEvent actionEvent) throws Exception {
        short key=0;
        Button x = (Button) actionEvent.getSource();
        String s =x.getId().substring(4);
        int index =Integer.parseInt(s);

        if(index<40){
            String keyS = x.getText();
            key = (short) keyS.charAt(0);
            if (key>64)
            {
                key = Cap_look? key: (short) (key +  32);
            }
        }
        else {
            switch (index){
                case 50: key = 9;break;
                case 51:{
                    if (Cap_look) {
                        Cap_look=false;
                        x.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1);");
                        System.out.println("butt is disarmed");
                    }
                    else {
                        Cap_look=true;
                        x.setStyle( "    -fx-text-fill: white;\n" +
                                    "    -fx-font-family: \"Consolas, Monaco, monospace\",Georgia,Serif;\n" +
                                    "    -fx-font-weight: bold;\n" +
                                    "    -fx-background-color: linear-gradient(#212121   , #757575   );\n" +
                                    "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
                        System.out.println("butt is armed");
                    }
                }break;
                case 52: key = 13; break;
                case 53:break;
                case 54:break;
                case 55: key = 32; break;
                case 56: key = 127; break;
            }
        }
       System.out.println(key);

        machine.keyPress(key);


       
    }
}
