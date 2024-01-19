/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw3;

/**
 * @author siddhesh
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CCModel {
	ObservableList<Case> caseList = FXCollections.observableArrayList(); 			//a list of case objects
	ObservableMap<String, Case> caseMap = FXCollections.observableHashMap();		//map with caseNumber as key and Case as value
	ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap();	//map with each year as a key and a list of all cases dated in that year as value. 
	ObservableList<String> yearList = FXCollections.observableArrayList();			//list of years to populate the yearComboBox in ccView

	/** readCases() performs the following functions:
	 * It creates an instance of CaseReaderFactory, 
	 * invokes its createReader() method by passing the filename to it, 
	 * and invokes the caseReader's readCases() method. 
	 * The caseList returned by readCases() is sorted 
	 * in the order of caseDate for initial display in caseTableView. 
	 * Finally, it loads caseMap with cases in caseList. 
	 * This caseMap will be used to make sure that no duplicate cases are added to data
	 * @param filename
	 */
	void readCases(String filename) {
		//write your code here
		CaseReaderFactory cReaderFactory = new CaseReaderFactory();
		CaseReader cReader = cReaderFactory.createReader(filename);
		caseList = FXCollections.observableArrayList(cReader.readCases());			//Initializing caseList
		Collections.sort(caseList);
		for(int i = 0; i < caseList.size(); i++)
		{
			caseMap.put(caseList.get(i).getCaseNumber(), caseList.get(i));			//Initializing caseMap
		}
	}
	
	/** writeCases() writes caseList elements in a TSV file. If the writing is successful,
	 * it returns true. In case of IOException, it returns false.
	 */
	
	boolean writeCases(String filename) {
		//write your code here
		try {
			FileWriter fw = new FileWriter(filename);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < caseList.size(); i++) {
				String cTitle = caseList.get(i).getCaseTitle();
				String cType = caseList.get(i).getCaseType();
				String cDate = caseList.get(i).getCaseDate();
				String cNumber = caseList.get(i).getCaseNumber();
				String cLink = caseList.get(i).getCaseLink();
				String cCategory = caseList.get(i).getCaseCategory();
				String cNotes = caseList.get(i).getCaseNotes();
				
				if(cLink.isBlank()) {
					cLink = " ";
				}
				if(cCategory.isBlank()) {
					cCategory = " ";
				}
				if(cNotes.isBlank()) {
					cNotes = " ";
				}
				sb.append(cDate + "\t" + cTitle + "\t" + cType + "\t" + cNumber + "\t" + cLink + "\t" + cCategory + "\t" + cNotes + "\n");
				fw.write(sb.toString());
			}
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** buildYearMapAndList() performs the following functions:
	 * 1. It builds yearMap that will be used for analysis purposes in Cyber Cop 3.0
	 * 2. It creates yearList which will be used to populate yearComboBox in ccView
	 * Note that yearList can be created simply by using the keySet of yearMap.
	 */
	void buildYearMapAndList() {
		//write your code here
		for(int i  = 0; i < caseList.size(); i++)
		{
			String yr = caseList.get(i).getCaseDate().substring(0,4);
			if(yearMap.containsKey(yr) == false) {
				List<Case> yrCases = new ArrayList<>();
				yrCases.add(caseList.get(i));
				yearMap.put(yr, yrCases);													//Loading yearMap
			}
			else {
				yearMap.get(yr).add(caseList.get(i));
			}	
		}
		yearList = FXCollections.observableArrayList(new ArrayList<>(yearMap.keySet()));	//Loading yearList
	}

	/**searchCases() takes search criteria and 
	 * iterates through the caseList to find the matching cases. 
	 * It returns a list of matching cases.
	 */
	List<Case> searchCases(String title, String caseType, String year, String caseNumber) {
		//write your code here
		List<Case> foundC = new ArrayList<>();
		for(int i = 0; i < caseList.size(); i++) {
			String cTitle = caseList.get(i).getCaseTitle().toLowerCase();
			String cType = caseList.get(i).getCaseType().toLowerCase();
			String cYear = caseList.get(i).getCaseDate().substring(0,4).toLowerCase();
			String cNumber = caseList.get(i).getCaseNumber().toLowerCase();
			
			StringBuilder temp = new StringBuilder();
			temp.append(cTitle + "\n").append(cType + "\n").append(cYear + "\n").append(cNumber + "\n");
			
			if(title == null) {
				title = " ";
			}
			if(caseType == null) {
				caseType =  " ";
			}
			if(year == null) {
				year = " ";
			}
			if(caseNumber == null) {
				caseNumber = " ";
			}
			
			if(temp.toString().contains(title.toLowerCase()) && temp.toString().contains(caseType.toLowerCase()) && temp.toString().contains(year.toLowerCase()) && temp.toString().contains(caseNumber.toLowerCase())) {
				foundC.add(caseList.get(i));
			}
		}
		
		return foundC;
	}
}
