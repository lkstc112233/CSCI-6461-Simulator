package UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;



public class MainPanelController {

    @FXML private Text Text_PC_Text;
    @FXML private  Text Text_PC_Label;




    public void handleTickButtonAction(ActionEvent actionEvent) {

    }

    public void handleAutoTickButtonAction(ActionEvent actionEvent) {
    }

    public void handleShowMagicButtonAction (ActionEvent actionEvent) throws Exception {

     //  Text_PC_Text.setText("333\n333\n333\n333");

        Parent root = FXMLLoader.load(getClass().getResource("ControlPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    public void handleShowFrontButtonAction(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FrontPanel.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }
}
