//Name: SIDDHESH BADHAN
//Andrew ID: sbadhan

package hw1;
/**
 * 
 * @author siddhesh
 *
 */
public class SearchEngine {

	/**searchTitle() takes a searchString and array of cases,
	 * searches for cases with searchString in their title,
	 * and if found, returns them in another array of cases.
	 * If no match is found, it returns null.
	 * Search is case-insensitive
	 * @param searchString
	 * @param cases
	 * @return case1
	 */
	Case[] searchTitle(String searchString, Case[] cases) {
		//write your code here

		int count = 0;
		Case[] case1;			//array for storing cases containing the searchString in their title

		for(int i = 0; i < cases.length; i++)
		{
			if(cases[i].caseTitle.toLowerCase().contains(searchString))
			{
				count++;
			}

		}

		case1 = new Case[count];

		if(count != 0)
		{
			int counter = 0;
			for(int i = 0; i < cases.length; i++)
			{
				if(cases[i].caseTitle.toLowerCase().contains(searchString))
				{

					case1[counter] = new Case(cases[i].caseDate,cases[i].caseTitle,cases[i].caseType,cases[i].caseNumber);
					counter = counter + 1;
				}

			}
			return case1;
		}
		else {
			return null;
		}
	}

	/**searchYear() takes year in YYYY format as search string,
	 * searches for cases that have the same year in their date,
	 * and returns them in another array of cases.
	 * If not found, it returns null.
	 * @param year
	 * @param cases
	 * @return case1
	 */
	Case[] searchYear(String year, Case[] cases) {
		//write your code here

		int count = 0;
		Case[] case1;			//array for storing cases which have the specified year in their date

		for(int i = 0; i < cases.length; i++)
		{
			if(cases[i].caseDate.contains(year))
			{
				count++;
			}

		}

		case1 = new Case[count];

		if(count != 0)
		{
			int counter = 0;
			for(int i = 0; i < cases.length; i++)
			{
				if(cases[i].caseDate.contains(year))
				{

					case1[counter] = new Case(cases[i].caseDate,cases[i].caseTitle,cases[i].caseType,cases[i].caseNumber);
					counter = counter + 1;
				}

			}
			return case1;
		}
		else {
			return null;
		}
	}

	/**searchCaseNumber() takes a caseNumber,
	 * searches for those cases that contain that caseNumber, 
	 * and returns an array of cases that match the search.
	 * If not found, it returns null.
	 * Search is case-insensitive.
	 * @param caseNumber
	 * @param cases
	 * @return case1
	 */
	Case[] searchCaseNumber(String caseNumber, Case[] cases) {
		//write your code here

		int count = 0;
		Case[] case1;		//array for storing cases that contain the given caseNumber

		for(int i = 0; i < cases.length; i++)
		{
			if(cases[i].caseNumber.toLowerCase().contains(caseNumber))
			{
				count++;
			}

		}

		case1 = new Case[count];

		if(count != 0)
		{
			int counter = 0;
			for(int i = 0; i < cases.length; i++)
			{
				if(cases[i].caseNumber.toLowerCase().contains(caseNumber))
				{

					case1[counter] = new Case(cases[i].caseDate,cases[i].caseTitle,cases[i].caseType,cases[i].caseNumber);
					counter = counter + 1;
				}

			}
			return case1;
		}
		else {
			return null;
		}
	}
}
