package increment.simulator.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FrontPanel extends Stage {
	MachineWrapper machine;
	public FrontPanel(MachineWrapper machineInput) {
		this.machine = machineInput;
		setTitle("Front Panel");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		for (int j = 0; j < 17; ++j) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100 / 17.);
			grid.getColumnConstraints().add(cc);
		}
		RowConstraints rc = new RowConstraints();
		for (int j = 0; j < 5; ++j) {
			rc = new RowConstraints();
			rc.setPercentHeight(57 / 5.);
			grid.getRowConstraints().add(rc);
		}
		for (int j = 0; j < 2; ++j) {
			rc = new RowConstraints();
			rc.setPercentHeight(43 / 2.);
			grid.getRowConstraints().add(rc);
		}
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 600, 210);
		scene.getStylesheets().add("increment/simulator/testing/buttonstyle.css");
		setScene(scene);
		
		for (int i = 0; i < 16; ++i) {
			RadioButton radio = new RadioButton();
			if (i < 12) {
				radio.setDisable(true);
				radio.selectedProperty().bind(machine.getAddressBulbsProperty(i));
				grid.add(radio, i, 1);
			}
			radio = new RadioButton();
			radio.setDisable(true);
			radio.selectedProperty().bind(machine.getValueBulbsProperty(i));
			grid.add(radio, i, 2);
			CheckBox check = new CheckBox();
			machine.getSwitchesProperty(i).bind(check.selectedProperty());
			grid.add(check, i, 3);
			grid.add(new Text(Integer.toString(i)), i, 0);
			grid.add(new Text(Integer.toString(i)), i, 4);
		}

		HBox box = new HBox();
		box.setSpacing(10);
		grid.add(box, 0, 5, 17, 1);
		
		Button button = new Button("Tick");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.forceTick();
			}
		});
		box.getChildren().add(button);
		button = new Button("Reset CU Status");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.resetCUStatus();
			}
		});
		box.getChildren().add(button);
		button = new Button("Load PC");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.forceLoadPC();
			}
		});
		box.getChildren().add(button);
		button = new Button("Load MAR");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.forceLoadMAR();
			}
		});
		box.getChildren().add(button);
		button = new Button("Load Data into Memory");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.loadDataIntoMemory();
			}
		});
		box.getChildren().add(button);
	}
}
