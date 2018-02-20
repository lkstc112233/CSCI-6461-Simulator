package increment.simulator.userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FrontPanel.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
