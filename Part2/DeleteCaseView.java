/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw2;

/**
 * @author siddhesh
 *
 */

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;

public class DeleteCaseView extends CaseView{

	/**
	 * 
	 */
	
	public DeleteCaseView(String header) {
		// TODO Auto-generated constructor stub
		super(header);
	}
	
	@Override
	Stage buildView() {
		stage.setTitle("Delete case");
		updateButton.setText("Delete case");
		caseDatePicker.setValue(LocalDate.now());
		Scene s = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
		stage.setScene(s);
		
		return stage;
	}

}
