package increment.simulator.userInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontPanelController {

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

        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/MainPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

    }

   /* public void handleMagicButtonAction(ActionEvent actionEvent) {
    }*/
    public void handleMagicButtonAction(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/ControlPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
