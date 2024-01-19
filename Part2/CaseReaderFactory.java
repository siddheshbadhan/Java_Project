/**
 * Andrew ID: sbadhan
 * Name: Siddhesh Badhan
 */

package hw2;

/**
 * @author siddhesh
 *
 */

public class CaseReaderFactory {
	
	CaseReader createReader(String filename) {
		CaseReader cReader = null;
		if(filename.contains("tsv"))
		{
			cReader = new TSVCaseReader(filename);
		}
		else if(filename.contains("csv"))
		{
			cReader = new CSVCaseReader(filename);
		}
		
		return cReader;
	}

}
