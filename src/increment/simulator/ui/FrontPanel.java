package increment.simulator.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
	    /*  this instruction is used to adjust the place of the children of grid, delimited when not need
	     *
	     *
	     */
		grid.setGridLinesVisible(true);
		Scene scene = new Scene(grid, 600, 400);
		scene.getStylesheets().add("increment/simulator/ui/buttonstyle.css");
		setScene(scene);
		/* centerlize  the node */
		grid.setAlignment(Pos.CENTER);



		for (int i = 0; i < 16; ++i) {
			RadioButton radio = new RadioButton();

			if (i < 12) {
				radio.setDisable(true);
				radio.selectedProperty().bind(machine.getAddressBulbsProperty(i));
				grid.add(radio, i, 1);
				grid.setHalignment(radio, HPos.CENTER);
			}
			radio = new RadioButton();
			radio.setDisable(true);
			radio.selectedProperty().bind(machine.getValueBulbsProperty(i));
			grid.add(radio, i, 2);
			grid.setHalignment(radio, HPos.CENTER);
			CheckBox check = new CheckBox();
			machine.getSwitchesProperty(i).bind(check.selectedProperty());
			Text text1 = new Text(Integer.toString(i));
			Text text2 = new Text(Integer.toString(i));
			grid.setHalignment(text1, HPos.CENTER);
			grid.add(text1, i, 0);
			grid.setConstraints(text2,i,4,1,1, HPos.CENTER, VPos.CENTER);
			grid.add(text2, i, 4);
		}

		HBox box = new HBox();
		box.setSpacing(10);
		grid.add(box, 0, 5, 17, 1);
		
		Button button = new Button("Tick");
		/*test for single button work */
		button.setStyle("  -fx-background-color:\n" +
				"        #3c7fb1,\n" +
				"        linear-gradient(#fafdfe, #e8f5fc),\n" +
				"        linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);");
		//grid.halignmentProperty();
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
