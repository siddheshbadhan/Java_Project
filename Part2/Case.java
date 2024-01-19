/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw2;

/**
 * @author siddhesh
 *
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Case implements Comparable<Case>{
	private final StringProperty caseDate = new SimpleStringProperty();
	private final StringProperty caseTitle = new SimpleStringProperty();
	private final StringProperty caseType = new SimpleStringProperty();
	private final StringProperty caseNumber = new SimpleStringProperty();
	private final StringProperty caseLink = new SimpleStringProperty();
	private final StringProperty caseCategory = new SimpleStringProperty();
	private final StringProperty caseNotes = new SimpleStringProperty();
	
	Case (String caseDate, String caseTitle, String caseType, String caseNumber, String caseLink, String caseCategory, String caseNotes){
		this.caseDate.set(caseDate);
		this.caseTitle.set(caseTitle);
		this.caseType.set(caseType);
		this.caseNumber.set(caseNumber);
		this.caseLink.set(caseLink);
		this.caseCategory.set(caseCategory);
		this.caseNotes.set(caseNotes);
	}
	
	public final String getCaseDate() {
		return caseDate.get();
	}
	
	public final void setCaseDate(String date) {
		this.caseDate.set(date);
	}
	
	public final StringProperty caseDateProperty() {
		return caseDate;
	}
	
	public final String getCaseTitle() {
		return caseTitle.get();
	}
	
	public final void setCaseTitle(String title) {
		this.caseTitle.set(title);
	}
	
	public final StringProperty caseTitleProperty() {
		return caseTitle;
	}
	
	public final String getCaseType() {
		return caseType.get();
	}
	
	public final void setCaseType(String type) {
		this.caseType.set(type);
	}
	
	public final StringProperty caseTypeProperty() {
		return caseType;
	}
	
	public final String getCaseNumber() {
		return caseNumber.get();
	}
	
	public final void setCaseNumber(String number) {
		this.caseNumber.set(number);
	}
	
	public final StringProperty caseNumberProperty() {
		return caseNumber;
	}
	
	public final String getCaseLink() {
		return caseLink.get();
	}
	
	public final void setCaseLink(String link) {
		this.caseLink.set(link);
	}
	
	public final StringProperty caseLinkProperty() {
		return caseLink;
	}
	
	public final String getCaseCategory() {
		return caseCategory.get();
	}
	
	public final void setCaseCategory(String category) {
		this.caseCategory.set(category);
	}
	
	public final StringProperty caseCategoryProperty() {
		return caseCategory;
	}
	
	public final String getCaseNotes() {
		return caseNotes.get();
	}
	
	public final void setCaseNotes(String notes) {
		this.caseNotes.set(notes);
	}

	public final StringProperty caseNotesProperty() {
		return caseNotes;
	}
	
	@Override
	public String toString() {
		return caseNumber.get();
	}
	
	@Override
	public int compareTo(Case c) {
		try {
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(this.getCaseDate());
			Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(c.getCaseDate());
			return d1.compareTo(d2);
		}
		catch(ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	

}
