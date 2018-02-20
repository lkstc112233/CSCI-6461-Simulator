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
        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/FrontPanel.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 400));
    	machine = new MachineWrapper(new Machine());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
