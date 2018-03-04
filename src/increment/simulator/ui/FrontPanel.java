package increment.simulator.ui;

import increment.simulator.userInterface.MachineWrapper;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
			cc.setPercentWidth(75. / 17.);
			grid.getColumnConstraints().add(cc);
		}
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(12.5);
		grid.getColumnConstraints().add(cc);
		cc = new ColumnConstraints();
		cc.setPercentWidth(12.5);
		grid.getColumnConstraints().add(cc);
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
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	    /*  this instruction is used to adjust the place of the children of grid, delimited when not need
	     *
	     *
	     */
		grid.setGridLinesVisible(false);
		Scene scene = new Scene(grid, 800, 400);
		scene.getStylesheets().add("/res/css/buttonstyle.css");
		scene.getStylesheets().add("/res/css/background.css");
		setScene(scene);
		/* centerlize  the node */
		grid.setAlignment(Pos.CENTER);



		for (int i = 0; i < 16; ++i) {
			RadioButton radio = new RadioButton();

			if (i < 12) {
				radio.setDisable(true);
				radio.selectedProperty().bind(machine.getAddressBulbsProperty(i));
				grid.add(radio, i, 1);
				GridPane.setHalignment(radio, HPos.CENTER);
			}
			radio = new RadioButton();
			radio.setDisable(true);
			radio.selectedProperty().bind(machine.getValueBulbsProperty(i));
			grid.add(radio, i, 2);
			GridPane.setHalignment(radio, HPos.CENTER);
			CheckBox check = new CheckBox();
			grid.add(check, i, 3);
			GridPane.setConstraints(check,i,3,1,1,HPos.CENTER,VPos.CENTER);
			machine.getSwitchesProperty(i).bind(check.selectedProperty());
			Text text1 = new Text(Integer.toString(i));
			Text text2 = new Text(Integer.toString(i));
			GridPane.setHalignment(text1, HPos.CENTER);
			grid.add(text1, i, 0);
			GridPane.setConstraints(text2,i,4,1,1, HPos.CENTER, VPos.CENTER);
			grid.add(text2, i, 4);
		}
		VBox groupBox = new VBox();
		grid.add(groupBox, 16, 0, 2, 7);
		groupBox.setSpacing(5);
		ToggleGroup group = new ToggleGroup();
		RadioButton selection = new RadioButton("PC");
		selection.setToggleGroup(group);
		selection.setSelected(true);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRadioSwitch(0);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("Memory");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRadioSwitch(2);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("GPRF");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRadioSwitch(5);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("IRF");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRadioSwitch(4);
				machine.forceUpdate();
			}
		});
		
		// New group for register selection.
		groupBox = new VBox();
		group = new ToggleGroup();
		selection = new RadioButton("0");
		groupBox.setSpacing(5);
		selection.setSelected(true);
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRegisterRadioSwitch(0);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("1");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRegisterRadioSwitch(1);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("2");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRegisterRadioSwitch(2);
				machine.forceUpdate();
			}
		});
		selection = new RadioButton("3");
		selection.setToggleGroup(group);
		groupBox.getChildren().add(selection);
		selection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.setRegisterRadioSwitch(3);
				machine.forceUpdate();
			}
		});
		grid.add(groupBox, 18, 0, 1, 7);
		
		

		HBox box = new HBox();
		box.setSpacing(10);
		grid.add(box, 0, 5, 17, 1);
		

		Button button = new Button("Circle");
		/*test for single button work */

		//grid.halignmentProperty();

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.forceTick();
			}
		});
		box.getChildren().add(button);
		RadioButton indicator = new RadioButton();
		
		indicator.setDisable(true);
		indicator.selectedProperty().bind(machine.getPausedProperty());
		box.getChildren().add(indicator);
		button = new Button("Pause");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.pauseOrRestore();
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
		button = new Button("IPL");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.IPLButton();
			}
		});
		box.getChildren().add(button);
		button = new Button("Load");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				machine.forceLoad();
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
	}
}
