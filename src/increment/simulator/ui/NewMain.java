package increment.simulator.ui;

import java.util.HashMap;
import java.util.Map;

import increment.simulator.Machine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The main frame. Using javafx techs.
 * @author Xu Ke
 *
 */
public class NewMain extends Application {
	MachineWrapper machine;
	Map<String, Text> mapping;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Virtual Machine");
		
		machine = new MachineWrapper(new Machine());
		mapping = new HashMap<>();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 500, 400);
		primaryStage.setScene(scene);
		
		HBox box = new HBox();
		box.getChildren().add(new Text("Tick: "));
		Text textBox = new Text();
		textBox.textProperty().bind(machine.getTickProperty().asString());
		box.getChildren().add(textBox);
		grid.add(box, 0, 0);

		box = new HBox();
		box.getChildren().add(new Text("PC: "));
		textBox = new Text();
		textBox.textProperty().bind(machine.getProgramCounterProperty());
		box.getChildren().add(textBox);
		grid.add(box, 0, 1);

//		Label userName = new Label("User Name:");
//		grid.add(userName, 0, 1);
//
//		TextField userTextField = new TextField();
//		grid.add(userTextField, 1, 1);
//
//		Label pw = new Label("Password:");
//		grid.add(pw, 0, 2);
//
//		PasswordField pwBox = new PasswordField();
//		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Tick");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				machine.tick();
			}});
		grid.add(btn, 0, 2);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
