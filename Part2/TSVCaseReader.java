/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw2;

/**
 * @author siddhesh
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSVCaseReader extends CaseReader{
	
	TSVCaseReader(String filename){
		super(filename);
	}
	
	@Override
	List<Case> readCases(){
		List<Case> caseList = new ArrayList<>();
		StringBuilder cReader = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				cReader.append(input.nextLine() + "\n");
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String temp[] = cReader.toString().split("\n");
		
		for(int j = 0; j < temp.length; j++)
		{
			String[] temp1 = temp[j].toString().split("\t");
			Case cs = new Case(temp1[0].trim(), temp1[1].trim(), temp1[2].trim(), temp1[3].trim(), temp1[4].trim(), temp1[5].trim(), temp1[6].trim());
			caseList.add(cs);
			
		}
		
		return caseList;

	}

}
