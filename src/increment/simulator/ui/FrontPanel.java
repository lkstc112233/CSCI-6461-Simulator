package increment.simulator.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class FrontPanel extends Stage {
	public FrontPanel(MachineWrapper machine) {
		setTitle("Front Panel");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		for (int j = 0; j < 17; ++j) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100 / 17.);
			grid.getColumnConstraints().add(cc);
		}
		RowConstraints rc = new RowConstraints();
		for (int j = 0; j < 3; ++j) {
			rc = new RowConstraints();
			rc.setPercentHeight(30 / 3.);
			grid.getRowConstraints().add(rc);
		}
		for (int j = 0; j < 5; ++j) {
			rc = new RowConstraints();
			rc.setPercentHeight(70 / 5.);
			grid.getRowConstraints().add(rc);
		}
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 900, 300);
		setScene(scene);
		
		for (int i = 0; i < 16; ++i) {
			RadioButton radio = new RadioButton();
			if (i < 12) {
				radio.setDisable(true);
				radio.selectedProperty().bind(machine.getAddressBulbsProperty(i));
				grid.add(radio, i, 0);
			}
			radio = new RadioButton();
			radio.setDisable(true);
			radio.selectedProperty().bind(machine.getValueBulbsProperty(i));
			grid.add(radio, i, 1);
			CheckBox check = new CheckBox();
			machine.getSwitchesProperty(i).bind(check.selectedProperty());
			grid.add(check, i, 2);
		}
		
	}
}
