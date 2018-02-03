package increment.simulator.ui;

import java.util.HashMap;
import java.util.Map;

import increment.simulator.Machine;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
		grid.setHgap(50);
		grid.setMinWidth(200);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 800, 600);
		primaryStage.setScene(scene);
		
		grid.add(getBox(grid, "Tick: ", machine.getTickProperty().asString()), 0, 0);
		grid.add(getBox(grid, "PC: ", machine.getProgramCounterProperty()), 0, 1);
		grid.add(getBox(grid, "BUS: ", machine.getBusProperty()), 0, 2);
		grid.add(getBox(grid, "MAR: ", machine.getMemoryAddressRegisterProperty()), 0, 3);
		grid.add(getBox(grid, "MBR: ", machine.getMemoryBufferRegisterProperty()), 0, 4);
		grid.add(getBox(grid, "IR: ", machine.getInstructionRegisterProperty()), 0, 5);
		grid.add(getBox(grid, "GPRF: ", machine.getGeneralPurposeRegisterFileProperty()), 1, 0, 1, 2);
		grid.add(getBox(grid, "IRF: ", machine.getIndexRegisterFileProperty()), 1, 2, 1, 2);
		grid.add(getScrollBox(grid, "Memory: ", machine.getMemoryProperty()), 2, 0, 1, 6);
		
		Button btn = new Button("Tick");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				machine.tick();
			}});
		grid.add(btn, 0, 6);
		primaryStage.show();
	}

	private Node getBox(GridPane grid, String hint, ObservableValue<? extends String> binding) {
		VBox box = new VBox();
		box.getChildren().add(new Text(hint));
		Text textBox = new Text();
		textBox.textProperty().bind(binding);
		box.getChildren().add(textBox);
		return box;
	}
	
	private Node getScrollBox(GridPane grid, String hint, ObservableValue<? extends String> binding) {
		VBox box = new VBox();
		box.getChildren().add(new Text(hint));
		Text textBox = new Text();
		textBox.textProperty().bind(binding);
		ScrollPane scp = new ScrollPane(textBox);
		box.getChildren().add(scp);
		return box;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
