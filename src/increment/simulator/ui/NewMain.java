package increment.simulator.ui;

import java.util.HashMap;
import java.util.Map;

import increment.simulator.Machine;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The main frame. Using javafx techs.
 * 
 * @author Xu Ke
 *
 */
public class NewMain extends Application {
	MachineWrapper machine;
	Map<String, Text> mapping;
	Timeline automaticTick;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Virtual Machine");

		machine = new MachineWrapper(new Machine());
		mapping = new HashMap<>();

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		for (int j = 0; j < 3; ++j) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100 / 3.);
			grid.getColumnConstraints().add(cc);
		}
		for (int j = 0; j < 6; ++j) {
			RowConstraints cc = new RowConstraints();
			cc.setPercentHeight(100 / 6.);
			grid.getRowConstraints().add(cc);
		}
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

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
		grid.add(getBox(grid, "Control Unit: ", machine.getControlUnitProperty()), 1, 4);
		grid.add(getScrollBox(grid, "Memory: ", machine.getMemoryProperty()), 2, 0, 1, 6);

		automaticTick = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				machine.tick();
		    }
		}));
		automaticTick.setCycleCount(Timeline.INDEFINITE);
		
		HBox buttons = new HBox();
		buttons.setSpacing(10);
		grid.add(buttons, 0, 6, 3, 1);
		
		Button btn = new Button("Tick");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				machine.tick();
			}
		});
		buttons.getChildren().add(btn);
		btn = new Button("Auto tick on/off");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (Status.RUNNING == automaticTick.getStatus())
					automaticTick.pause();
				else
					automaticTick.play();				
			}
		});
		buttons.getChildren().add(btn);
		btn = new Button("Show magic panel");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getControlPanel().show();
			}
		});
		buttons.getChildren().add(btn);
		btn = new Button("Show front panel");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getFrontPanel().show();
			}
		});
		buttons.getChildren().add(btn);
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
	
	Stage controlPanel;
	private Stage getControlPanel() {
		if (controlPanel == null)
			controlPanel = new ControlPanel(machine);
		return controlPanel;
	}
	

	Stage frontPanel;
	private Stage getFrontPanel() {
		if (frontPanel == null)
			frontPanel = new FrontPanel(machine);
		return frontPanel;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
