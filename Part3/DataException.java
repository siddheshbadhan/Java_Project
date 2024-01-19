/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author siddhesh
 *
 */
public class DataException extends RuntimeException {

	
	/**
	 * @param message
	 */
	DataException(String message) {
		Alert al = new Alert(AlertType.ERROR);
		al.setTitle("Data Error");
		if(message.equals("TSV file missing data")) {
			
			al.setContentText(TSVCaseReader.invalidC + " cases rejected.\nThe file must have cases with \ntab separated date, title, type and case number!");
			al.showAndWait();
		}
		else if(message.equals("Missing Case Error")) {
			al.setContentText("Case must have date, title, type and number");
			al.showAndWait();
		}
		else if(message.equals("Duplicate Case")) {
			al.setContentText("Duplicate case number");
			al.showAndWait();
		}
		
		// TODO Auto-generated constructor stub
	}
	
}
