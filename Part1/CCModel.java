//Name: SIDDHESH BADHAN
//Andrew ID: sbadhan

package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * @author siddhesh
 *
 */
public class CCModel {
	Case[] cases;
	String[] fileData;

	/**loadData() takes filename as a parameter,
	 * reads the file and loads all 
	 * data as a String for each row in 
	 * fileData[] array
	 * @param filename
	 */
	void loadData(String filename) {
		//write your code here
		try {
			Scanner inputs = new Scanner (new File(filename));
			StringBuilder filecontent = new StringBuilder();
			
			while(inputs.useDelimiter("\n").hasNext())
			{
				filecontent.append(inputs.next()+"\n");
			}
			fileData = filecontent.toString().split("\n");
			inputs.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**loadCases() uses the data stored in fileData array
	 * and creates Case objects for each row.
	 * These cases are loaded into the cases array.
	 * Note that you may have to traverse the fileData array twice
	 * to be able to initialize the cases array's size.
	 */
	void loadCases() {
		//write your code here

		cases = new Case[fileData.length];
		String caseDate = " ";
		String caseTitle = " ";
		String caseType = " ";
		String caseNumber = " ";

		for (int i = 0; i < fileData.length; i++)
		{
			String s[] = fileData[i].split("\t");
			caseDate = s[0];
			caseTitle = s[1].split("\\(")[0];

			if(fileData[i].contains("(Federal)"))
			{
				caseType = "Federal";
			}
			else if(fileData[i].contains("(Administrative)"))
			{
				caseType = "Administrative";
			}
			else
			{
				caseType = "Unknown";
			}

			caseNumber = s[2];

			cases[i] = new Case(caseDate, caseTitle, caseType, caseNumber);

		}
	}


}
