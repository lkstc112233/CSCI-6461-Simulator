package increment.simulator.ui;

import increment.simulator.userInterface.MachineWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScreenPanel extends Stage {
	private MachineWrapper machine;
		
	public ScreenPanel(MachineWrapper machineInput){
		this.machine = machineInput;
		
		setTitle("Screen Panel");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(00.);
		grid.getColumnConstraints().add(cc);
		cc = new ColumnConstraints();
		cc.setPercentWidth(10.);
		grid.getColumnConstraints().add(cc);
		
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(100.);
		grid.getRowConstraints().add(rc);
		rc = new RowConstraints();
		rc.setPercentHeight(0.);
		grid.getRowConstraints().add(rc);
		
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 600, 450);
		scene.getStylesheets().add("/res/css/background.css");
		setScene(scene);
		
		Text screen = new Text();
		screen.textProperty().bind(machine.getScreenProperty());
		grid.add(screen, 0, 0);
		
		HBox buttons = new HBox();
		buttons.setSpacing(10);
		
		for (int i = 0; i <= 9; ++i) {
			Button button = new Button(Integer.toString(i));
			final int k = i;
			button.setOnAction(e -> machine.keyPress((short) (k + 48)));
			buttons.getChildren().add(button);	
		}
		Button button = new Button("Space");
		button.setOnAction(e -> machine.keyPress((short)' '));
		buttons.getChildren().add(button);	
		grid.add(buttons, 0, 1, 2, 1);
	}
}
