
/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw2;

/**
 * @author siddhesh
 *
 */

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CyberCop extends Application{

	public static final String DEFAULT_PATH = "data"; //folder name where data files are stored
	public static final String DEFAULT_HTML = "/CyberCop.html"; //local HTML
	public static final String APP_TITLE = "Cyber Cop"; //displayed on top of app

	CCView ccView = new CCView();
	CCModel ccModel = new CCModel();

	CaseView caseView; //UI for Add/Modify/Delete menu option

	GridPane cyberCopRoot;
	Stage stage;

	static Case currentCase; //points to the case selected in TableView.

	public static void main(String[] args) {
		launch(args);
	}

	/** start the application and show the opening scene */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Cyper Cop");
		cyberCopRoot = ccView.setupScreen();  
		setupBindings();
		Scene scene = new Scene(cyberCopRoot, ccView.ccWidth, ccView.ccHeight);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		primaryStage.show();
	}

	/** setupBindings() binds all GUI components to their handlers.
	 * It also binds disableProperty of menu items and text-fields 
	 * with ccView.isFileOpen so that they are enabled as needed
	 */
	void setupBindings() {
		//write your code here

		ccView.closeFileMenuItem.setDisable(true);
		ccView.addCaseMenuItem.setDisable(true);
		ccView.modifyCaseMenuItem.setDisable(true);
		ccView.deleteCaseMenuItem.setDisable(true);

		ccView.openFileMenuItem.setOnAction(new OpenFileMenuItemHandler());
		ccView.closeFileMenuItem.setOnAction(new CloseFileMenuItemHandler());
		ccView.searchButton.setOnAction(new SearchButtonHandler());
		ccView.clearButton.setOnAction(new ClearButtonHandler());
		ccView.exitMenuItem.setOnAction(new ExitMenuItemHandler());
		Collections.reverse(ccModel.caseList);

		ccView.caseTableView.getSelectionModel().selectedItemProperty().addListener(((observaleValue, oldValue, newValue) -> {
			if(newValue != null) {
				currentCase = newValue;
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0,4));
				ccView.caseTypeTextField.setText(currentCase.getCaseType());
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());

				if(currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {
					URL u = getClass().getClassLoader().getResource(DEFAULT_HTML);
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					} 
				}
				else if(currentCase.getCaseLink().toLowerCase().startsWith("http")) {
					ccView.webEngine.load(currentCase.getCaseLink());
				}
				else {
					URL u = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					}
				}

			}
		} ));

	}

	public class OpenFileMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			FileChooser fChooser = new FileChooser();
			fChooser.setTitle("Select file");
			fChooser.setInitialDirectory(new File(DEFAULT_PATH));
			fChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All", "*.*"));

			File f = fChooser.showOpenDialog(stage);
			if(f != null) {
				ccView.isFileOpen.setValue(true);
				ccView.closeFileMenuItem.setDisable(false);
				ccView.openFileMenuItem.setDisable(true);

				ccModel.readCases(f.getAbsolutePath());												//populates caseList
				ccModel.buildYearMapAndList();														//populates yearList
				Collections.sort(ccModel.yearList);
				Collections.reverse(ccModel.caseList);

				currentCase = ccModel.caseList.get(0);												//points currentCase to the first record in caseTableView
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseType());
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
				ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0,4));
				ccView.yearComboBox.setItems(ccModel.yearList);
				ccView.caseTableView.setItems(ccModel.caseList);

				//Update stage title
				String fName = f.getName();
				stage.setTitle("Cyber Cop: " + fName);
				ccView.messageLabel.setText(ccModel.caseList.size() + " cases");

				if(currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {
					URL u = getClass().getClassLoader().getResource(DEFAULT_HTML);
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					} 
				}
				else if(currentCase.getCaseLink().toLowerCase().startsWith("http")) {
					ccView.webEngine.load(currentCase.getCaseLink());
				}
				else {
					URL u = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					}
				}

				//Updates messageLabel
				if(ccView.isFileOpen.getValue()) {
					ccView.addCaseMenuItem.setDisable(false);
					ccView.modifyCaseMenuItem.setDisable(false);
					ccView.deleteCaseMenuItem.setDisable(false);

					ccView.addCaseMenuItem.setOnAction(new CaseMenuItemHandler());
					ccView.modifyCaseMenuItem.setOnAction(new CaseMenuItemHandler());
					ccView.deleteCaseMenuItem.setOnAction(new CaseMenuItemHandler());
				}
				else {
					ccView.addCaseMenuItem.setDisable(true);
					ccView.modifyCaseMenuItem.setDisable(true);
					ccView.deleteCaseMenuItem.setDisable(true);
				}
			}
		}
	}

	public class CloseFileMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// Clear all GUI elements
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.caseNumberTextField.clear();
			ccView.caseNotesTextArea.clear();
			ccView.yearComboBox.valueProperty().set(null);
			ccView.caseTableView.getItems().clear();


			if(currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {
				URL u = getClass().getClassLoader().getResource(DEFAULT_HTML);
				if(u != null) {
					ccView.webEngine.load(u.toExternalForm());
				} 
			}
			else if(currentCase.getCaseLink().toLowerCase().startsWith("http")) {
				ccView.webEngine.load(currentCase.getCaseLink());
			}
			else {
				URL u = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());
				if(u != null) {
					ccView.webEngine.load(u.toExternalForm());
				}
			}

			//Set isFileOPen to false
			stage.setTitle(APP_TITLE);
			ccView.isFileOpen.setValue(false);
			ccView.closeFileMenuItem.setDisable(true);
			ccView.openFileMenuItem.setDisable(false);

			ccView.addCaseMenuItem.setDisable(true);
			ccView.modifyCaseMenuItem.setDisable(true);
			ccView.deleteCaseMenuItem.setDisable(true);

		}
	}

	public class ExitMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Platform.exit();
		}
	}

	public class SearchButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			List<Case> foundC = new ArrayList<>();
			String cTitle = ccView.titleTextField.getText();
			String cType = ccView.caseTypeTextField.getText();
			String cYear = ccView.yearComboBox.getValue();
			String cNumber = ccView.caseNumberTextField.getText();
			foundC = ccModel.searchCases(cTitle,cType,cYear,cNumber);

			//Displays cases if data is found	
			if(foundC.size() == 0) {
				ObservableList<Case> fc = FXCollections.observableArrayList(foundC);
				ccView.caseTableView.setItems(fc);
				ccView.titleTextField.setText("");
				ccView.caseTypeTextField.setText("");
				ccView.caseNumberTextField.setText("");
				ccView.caseNotesTextArea.setText("");
				ccView.messageLabel.setText("Found 0 cases");

			} else {
				currentCase = foundC.get(0);
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseType());
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
				ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0,4));

				ccView.messageLabel.setText(foundC.size() + " cases");

				if(currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {
					URL u = getClass().getClassLoader().getResource(DEFAULT_HTML);
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					} 
				}
				else if(currentCase.getCaseLink().toLowerCase().startsWith("http")) {
					ccView.webEngine.load(currentCase.getCaseLink());
				}
				else {
					URL u = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());
					if(u != null) {
						ccView.webEngine.load(u.toExternalForm());
					}
				}

				ObservableList<Case> fCases = FXCollections.observableArrayList(foundC);
				ccView.caseTableView.setItems(fCases);

			}

		}
	}
	
	public class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//Clears the data
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.caseNumberTextField.clear();
			ccView.yearComboBox.valueProperty().set(null);
		}
	}
	
	public class CaseMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			MenuItem input = (MenuItem)event.getSource();
			String inputText = input.getText();

			if(inputText.equals("Add case")) {
				caseView = new AddCaseView("Add Case");
				stage = caseView.buildView();
				caseView.stage.show();

				caseView.updateButton.setOnAction(new AddButtonHandler());
				ccView.messageLabel.setText(ccModel.caseList.size() + "cases");

			} else if(inputText.equals("Modify case")) {
				caseView = new ModifyCaseView("Modify Case");
				stage = caseView.buildView();
				caseView.stage.show();

				String cTitle = currentCase.getCaseTitle();
				String cDate = currentCase.getCaseDate();
				String cType = currentCase.getCaseType();
				String cNumber = currentCase.getCaseNumber();
				String cCategory = currentCase.getCaseCategory();
				String cLink = currentCase.getCaseLink();
				String cNote = currentCase.getCaseNotes();

				caseView.titleTextField.setText(cTitle);
				caseView.caseTypeTextField.setText(cType);
				caseView.caseNotesTextArea.setText(cNote);
				caseView.caseNumberTextField.setText(cNumber);
				caseView.categoryTextField.setText(cCategory);
				caseView.caseLinkTextField.setText(cLink);
				caseView.caseDatePicker.setValue(LocalDate.parse(cDate));

				caseView.updateButton.setOnAction(new ModifyButtonHandler());

			} else if(inputText.equals("Delete case")) {
				caseView = new DeleteCaseView("Delete Case");
				stage = caseView.buildView();
				caseView.stage.show();
				
				String cTitle = currentCase.getCaseTitle();
				String cDate = currentCase.getCaseDate();
				String cType = currentCase.getCaseType();
				String cNumber = currentCase.getCaseNumber();
				String cCategory = currentCase.getCaseCategory();
				String cLink = currentCase.getCaseLink();
				String cNote = currentCase.getCaseNotes();

				caseView.titleTextField.setText(cTitle);
				caseView.caseTypeTextField.setText(cType);
				caseView.caseNotesTextArea.setText(cNote);
				caseView.caseNumberTextField.setText(cNumber);
				caseView.categoryTextField.setText(cCategory);
				caseView.caseLinkTextField.setText(cLink);
				caseView.caseDatePicker.setValue(LocalDate.parse(cDate));

				caseView.updateButton.setOnAction(new DeleteButtonHandler());
				
			}
			//Closes the stage when close button is pressed
			caseView.closeButton.setOnAction(actionEvent -> caseView.stage.close());
			
			//Clears all data from the view 
			caseView.clearButton.setOnAction(actionEvent ->{
				caseView.titleTextField.clear();
				caseView.caseTypeTextField.clear();
				caseView.caseDatePicker.setValue(null);
				caseView.caseNumberTextField.clear();
				caseView.categoryTextField.clear();
				caseView.caseLinkTextField.clear();
				caseView.caseNotesTextArea.clear();
			});
		}
	}

	public class AddButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String cTitle = caseView.titleTextField.getText();
			String cDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String cType = caseView.caseTypeTextField.getText();
			String cNumber = caseView.caseNumberTextField.getText();
			String cCategory = caseView.categoryTextField.getText();
			String cLink = caseView.caseLinkTextField.getText();
			String cNote = caseView.caseNotesTextArea.getText();

			Case newC = new Case(cDate, cTitle, cType, cNumber, cLink, cCategory, cNote);

			// Add new case to the caseList

			ccModel.caseList.add(newC);

			// Add new year if unique to the yearList
			StringBuilder uniqueYear = new StringBuilder();
			for(int i = 0; i < ccModel.yearList.size(); i++) {
				if(uniqueYear.toString().contains(ccModel.yearList.get(i)) == false) {
					uniqueYear.append(ccModel.yearList.get(i));
				}
			}

			if(uniqueYear.toString().contains(cDate.substring(0,4)) == false) {
				ccModel.yearList.add(cDate.substring(0,4));
			}
			ccView.messageLabel.setText(ccModel.caseList.size() + " cases");

		}
	}

	public class ModifyButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String cTitle = caseView.titleTextField.getText();
			String cDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String cType = caseView.caseTypeTextField.getText();
			String cNumber = caseView.caseNumberTextField.getText();
			String cCategory = caseView.categoryTextField.getText();
			String cLink = caseView.caseLinkTextField.getText();
			String cNote = caseView.caseNotesTextArea.getText();

			Case modifiedC = new Case(cDate, cTitle, cType, cNumber, cLink, cCategory, cNote);

			// Update caseList
			int num = ccModel.caseList.indexOf(currentCase);
			ccModel.caseList.set(num,modifiedC);

			// Update yearList if year is unique
			StringBuilder uniqueYear = new StringBuilder();
			for(int i = 0; i < ccModel.yearList.size(); i++) {
				if(uniqueYear.toString().contains(ccModel.yearList.get(i)) == false) {
					uniqueYear.append(ccModel.yearList.get(i));
				}
			}
			if(uniqueYear.toString().contains(cDate.substring(0,4)) == false) {
				ccModel.yearList.add(cDate.substring(0,4));
			}
		}
	}

	public class DeleteButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			Case cTemp = ccModel.caseList.get(ccModel.caseList.size() - 1);

			//Delete case from the caseList
			ccModel.caseList.remove(currentCase);
			ccModel.caseMap.remove(currentCase);
			
			//Delete year from yearList only if year is unique
			StringBuilder uniqueYear = new StringBuilder();
			for(int i = 0; i < ccModel.yearList.size(); i++) {
				if(uniqueYear.toString().contains(ccModel.yearList.get(i)) == false) {
					uniqueYear.append(ccModel.yearList.get(i));
				}
			}
			if(uniqueYear.toString().contains(currentCase.getCaseDate().substring(0,4)) == false) {
				ccModel.yearList.remove(currentCase.getCaseDate().substring(0,4));
			}

			ccView.messageLabel.setText(ccModel.caseList.size() + " cases");
			currentCase = cTemp;
		}
	}
}






