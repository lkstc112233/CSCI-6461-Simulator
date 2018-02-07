package increment.simulator.ui;

import java.util.HashMap;
import java.util.Map;

import increment.simulator.Machine;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
	StringProperty programProperty;
	StringProperty programAddressProperty;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Virtual Machine");

		machine = new MachineWrapper(new Machine());
		mapping = new HashMap<>();

		programProperty = new SimpleStringProperty();
		programAddressProperty = new SimpleStringProperty();

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
		grid.add(getBox(grid, "Control Unit: ", machine.getControlUnitProperty()), 1, 4);
		grid.add(getScrollBox(grid, "Memory: ", machine.getMemoryProperty()), 2, 0, 1, 6);

		automaticTick = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				machine.tick();
		    }
		}));
		automaticTick.setCycleCount(Timeline.INDEFINITE);
		
		Button btn = new Button("Tick");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				machine.tick();
			}
		});
		grid.add(btn, 0, 6);
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
		grid.add(btn, 1, 6);
		btn = new Button("Show control panel");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getControlPanel().show();
			}
		});
		grid.add(btn, 2, 6);
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
		if (controlPanel != null)
			return controlPanel;
		controlPanel = new Stage();

		controlPanel.setTitle("Control Panel");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(10.);
		grid.getColumnConstraints().add(cc);
		cc = new ColumnConstraints();
		cc.setPercentWidth(90.);
		grid.getColumnConstraints().add(cc);
		
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(10.);
		grid.getRowConstraints().add(rc);
		for (int j = 0; j < 6; ++j) {
			rc = new RowConstraints();
			rc.setPercentHeight(90 / 6.);
			grid.getRowConstraints().add(rc);
		}
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 600, 450);
		controlPanel.setScene(scene);
		
		grid.add(new Text("To Address\nbeginning at:"), 0, 0);
		TextArea textArea = new TextArea();
		textArea.setWrapText(false);
		textArea.textProperty().bindBidirectional(programAddressProperty);
		grid.add(textArea, 1, 0);
		
		grid.add(new Text("Program:"), 0, 1);
		textArea = new TextArea();
		textArea.setWrapText(true);
		textArea.textProperty().bindBidirectional(programProperty);
		grid.add(textArea, 1, 1, 1, 5);
		
		Button button = new Button("Load Program");
		button.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				machine.putProgram(programAddressProperty.get(), programProperty.get());
			}
		});
		grid.add(button, 0, 6, 2, 1);
		
		return controlPanel;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
