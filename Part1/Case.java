//Name: SIDDHESH BADHAN
//Andrew ID: sbadhan

package hw1;
/**
 * 
 * @author siddhesh
 *
 */
public class Case {
	String caseDate; //date in YYYY-mm-dd format
	String caseTitle;
	String caseType;
	String caseNumber;

	Case(String caseDate, String caseTitle, String caseType, String caseNumber) {
		//write your code here
		this.caseDate = caseDate;
		this.caseTitle = caseTitle;
		this.caseType = caseType;
		this.caseNumber = caseNumber;
	}

	/** getYear() is an optional method to extract year
	 * from the caseDate. It can be useful 
	 * for printing yearWise summary. 
	 * @return year
	 */

	int getYear() {
		//write your code here
		String date = this.caseDate;
		String s[] = date.trim().split("-");
		int year = Integer.parseInt(s[0]);
		return year;
	}
}