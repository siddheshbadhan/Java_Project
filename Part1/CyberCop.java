//Name: SIDDHESH BADHAN
//Andrew ID: sbadhan

package hw1;

import java.util.Scanner;
/**
 * 
 * @author siddhesh
 *
 */
public class CyberCop {

	public static final String DATAFILE = "data/FTC-cases-TSV.txt";
	CCModel ccModel = new CCModel();
	SearchEngine searchEngine = new SearchEngine();

	Scanner input = new Scanner(System.in);

	/**main() instantiates CyberCop and then invokes dataManager's loadData
	 * and loadCases() methods
	 * It then invokes showMenu to get user input
	 * @param args
	 */
	//Do not change this method
	public static void main(String[] args) {
		CyberCop cyberCop = new CyberCop();
		cyberCop.ccModel.loadData(DATAFILE);
		cyberCop.ccModel.loadCases();
		cyberCop.showMenu();
	}

	/**showMenu() shows the menu. 
	 * Based on the user choice, it invokes one of the methods:
	 * printSearchResults(), printCaseTypeSummary(), or printYearwiseSummary()
	 * The program exits when user selects Exit option. 
	 * See the hand-out for the expected layout of menu-UI
	 */
	void showMenu() {
		//write your code here
		int num = 0;
		int flag = 0;
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.println("*** Welcome to CyberCop! ***");
			System.out.println("1. Search cases for a company");
			System.out.println("2. Search cases in a year");
			System.out.println("3. Search case number");
			System.out.println("4. Print Case-type Summary");
			System.out.println("5. Print Year-wise Summary");
			System.out.println("6. Exit");
			num  = input.nextInt();
			input.nextLine();
			switch(num)
			{
			case 1:
				System.out.println("------------------------------------------------------------------");
				System.out.println("Enter search string");
				String searchString = input.nextLine();
				Case[] arr1 = searchEngine.searchTitle(searchString, ccModel.cases);		//array of cases for the specified company
				printSearchResults(searchString, arr1);
				break;

			case 2:
				System.out.println("------------------------------------------------------------------");
				System.out.println("Enter search year as YYYY");
				String year = input.nextLine();
				Case[] arr2 = searchEngine.searchYear(year, ccModel.cases);					//array of cases in the given year
				printSearchResults(year, arr2);
				break;

			case 3:
				System.out.println("------------------------------------------------------------------");
				System.out.println("Enter case number");
				String caseNumber = input.nextLine();
				Case[] arr3 = searchEngine.searchCaseNumber(caseNumber, ccModel.cases);		//array of cases for the specified case number
				printSearchResults(caseNumber, arr3);
				break;

			case 4:
				printCaseTypeSummary();
				break;

			case 5:
				printYearwiseSummary();
				break;

			case 6:
				flag = 1;
				break;

			default :
				System.out.println("Invalid Input");
				break;

			}
		}
		while(flag == 0);

		
		input.close();
	}

	/**printSearcjResults() takes the searchString and array of cases as input
	 * and prints them out as per the format provided in the handout
	 * @param searchString
	 * @param cases
	 */
	void printSearchResults(String searchString, Case[] cases) {
		//write your code here

		if(cases == null)
		{
			System.out.println("Sorry, no search results found for " + searchString);
			System.out.println("------------------------------------------------------------------");

		}
		else
		{
			System.out.println("------------------------------------------------------------------");
			System.out.println(cases.length + " case(s) found for " + searchString);
			System.out.println("------------------------------------------------------------------");
			System.out.println("#. Last update Case Title \t\t\t\t\t\t Case Type \t Case/File Number");
			System.out.println("------------------------------------------------------------------");
			for(int i = 0; i < cases.length; i++)
			{
				System.out.printf("%d. %-11s %-55s\t %s\t %6s", i+1, cases[i].caseDate, cases[i].caseTitle, cases[i].caseType, cases[i].caseNumber);

			}
			System.out.println("------------------------------------------------------------------");
		}

	}

	/**printCaseTypeSummary() prints a summary of
	 * number of cases of different types as per the 
	 * format given in the handout.
	 */
	void printCaseTypeSummary() {
		//write your code here
		int admin = 0;
		int federal = 0;
		int unkwn = 0;

		System.out.println("--------------------------------------------------------");
		System.out.println("*** Case Type Summary Report ***");

		for(int i = 0; i < ccModel.cases.length;  i++)
		{
			if(ccModel.cases[i].caseType.contains("Administrative"))
			{
				admin++;
			}
			else if(ccModel.cases[i].caseType.contains("Federal"))
			{
				federal++;
			}
			else
			{
				unkwn++;
			}
		}

		System.out.println("No. of Administrative cases: " + admin);
		System.out.println("No. of Federal cases: " + federal);
		System.out.println("No. of unknown case types: " + unkwn);
		System.out.println("--------------------------------------------------------");
	}

	/**printYearWiseSummary() prints number of cases in each year
	 * as per the format given in the handout
	 */
	void printYearwiseSummary() {
		//write your code here
		int[] yearcount;
		StringBuilder uniqueYear = new StringBuilder();

		for(int i = 0; i < ccModel.cases.length; i++)
		{
			//String s = ccModel.cases[i].caseDate;
			String s[] = ccModel.cases[i].caseDate.trim().split("-");
			String year = s[0];
			if(! uniqueYear.toString().contains(year))
			{
				uniqueYear.append(year + ",");
			}

		}
		String[] years = uniqueYear.toString().split(",");		//array for maintaining unique years

		yearcount = new int[years.length];						//array for maintaining count of cases year wise
		for(int i = 0; i < ccModel.cases.length; i++)
		{
			for (int j = 0; j < years.length; j++)
			{
				if(ccModel.cases[i].caseDate.contains(years[j]))
				{
					yearcount[j]++;
				}
			}

		}

		System.out.println("---------------------------------------------------------------------");
		System.out.println("\t\t\t" + "*** Year-wise summary Report ***");
		System.out.println("\t\t\t" + "*** Number of FTC cases per year ***");

		for(int i = 0; i < years.length; i++)
		{
			if(i % 5 == 0)
			{
				System.out.print("\n");
				System.out.print("   ");
			}
			System.out.printf("       %s: %2d",years[i],yearcount[i]);
		}

		System.out.print("\n");
		System.out.println("---------------------------------------------------------------------");

	}

}
