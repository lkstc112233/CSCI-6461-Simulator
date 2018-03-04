package increment.simulator.ui;

import increment.simulator.userInterface.MachineWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlPanel extends Stage {
	private MachineWrapper machine;
	
	private StringProperty programProperty;
	private StringProperty programAddressProperty;
	
	public ControlPanel(MachineWrapper machineInput){
		this.machine = machineInput;

		programProperty = new SimpleStringProperty();
		programAddressProperty = new SimpleStringProperty();
		
		setTitle("Magic Panel");
		
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
		scene.getStylesheets().add("/res/css/background.css");
		setScene(scene);
		
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
	}
}
