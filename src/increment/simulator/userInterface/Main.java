package increment.simulator.userInterface;

import increment.simulator.Machine;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
	private MachineWrapper machine;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	Font.loadFont(getClass().getResource("/res/font/Menlo.ttf").toExternalForm(), 10);
    	machine = new MachineWrapper(new Machine());
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/FrontPanel.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<FrontPanelController>getController().setMachine(machine);
        FrontPanelController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/res/css/background.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("CSCI 6461 Simulator Front Panel");

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.keyHandler(keyEvent);
                System.out.println(keyEvent.getCode());
             primaryStage.getScene().setOnKeyReleased(event -> {});

            }
        });
        primaryStage.show();

    }

    private EventHandler keychanged(){


        return null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
