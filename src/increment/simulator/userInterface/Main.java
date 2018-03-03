package increment.simulator.userInterface;

import increment.simulator.Machine;
import increment.simulator.ui.MachineWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private MachineWrapper machine;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	machine = new MachineWrapper(new Machine());
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/FrontPanel.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<FrontPanelController>getController().setMachine(machine);
        Scene scene = new Scene(root, 1000, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CSCI 6461 Simulator Front Panel");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
