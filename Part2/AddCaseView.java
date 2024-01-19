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

public class AddCaseView extends CaseView{
	
	/**
	 * 
	 */
	public AddCaseView(String header){
		// TODO Auto-generated constructor stub
		super(header);
	}
	
	@Override
	Stage buildView() {
		stage.setTitle("Add case");
		updateButton.setText("Add case");
		caseDatePicker.setValue(LocalDate.now());							//initialized to show current date
		Scene s = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
		stage.setScene(s);
		
		return stage;
		
	}

}
