package increment.simulator.testing;

import increment.simulator.BulbSet;
import increment.simulator.SingleCable;
import increment.simulator.SwitchesSet;
import javafx.application.Application;
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
import javafx.stage.Stage;

public class UiFrameTesting extends Application {
	RadioButton[] bulbs;
	CheckBox[] switches;
	SwitchesSet setSwitch;
	BulbSet setBulb;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Virtual Machine Test");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		for (int j = 0; j < 20; ++j) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100 / 20.);
			grid.getColumnConstraints().add(cc);
		}
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 800, 600);
		primaryStage.setScene(scene);

		bulbs = new RadioButton[20];
		switches = new CheckBox[20];
				
		setSwitch = new SwitchesSet(20);
		setBulb = new BulbSet(20);
		
		SingleCable connection = new SingleCable(20);
		
		setSwitch.connectPort("output", connection);
		setBulb.connectPort("input", connection);
		
		for (int i = 0; i < 20; ++i) {
			grid.add(bulbs[i] = new RadioButton(), i, 0);
			grid.add(switches[i] = new CheckBox(), i, 1);
			bulbs[i].setDisable(true);
			final int k = i;
			switches[i].setOnAction(new EventHandler<ActionEvent>(){
				int r = k;
				@Override
				public void handle(ActionEvent event) {
					setSwitch.flipBit(r, switches[r].isSelected());
					updateUi();
				}});
		}

		Button btn = new Button("Tick");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tick();
			}

			private void tick() {
				setSwitch.tick();
				setBulb.tick();
				boolean repeat = true;
				while (repeat) {
					repeat = setSwitch.evaluate();
					repeat |= setBulb.evaluate();
				}
				updateUi();
			}
		});
		grid.add(btn, 0, 2, 4, 1);
		updateUi();
		primaryStage.show();
	}

	protected void updateUi() {
		for (int i = 0; i < 20; ++i) {
			bulbs[i].setSelected(setBulb.getBit(i));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
