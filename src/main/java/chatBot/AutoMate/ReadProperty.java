package chatBot.AutoMate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadProperty {
	
	public static String convertXSSFCellToString(XSSFCell cell) {
		String cellValue = null;
		if (cell != null) {
			cellValue = cell.toString();
			cellValue = cellValue.trim();
		} else {
			cellValue = "";
		}
		return cellValue;
	}
	
	public static Hashtable<String, Integer> findRowColumnCount(
			XSSFSheet sheet, Hashtable<String, Integer> rowColumnCount) {

		XSSFRow row = null;
		int rows;
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0;
		int tmp = 0;
		int counter = 0;
		String temp = null;

		for (int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				temp = convertXSSFCellToString(row.getCell(0));
				if (!temp.equals("")) {
					counter++;
				}
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}

		rowColumnCount.put("RowCount", counter);
		rowColumnCount.put("ColumnCount", cols);

		return rowColumnCount;
	}
	public static Hashtable<String, Integer> readExcelHeaders(XSSFSheet sheet,
			Hashtable<String, Integer> excelHeaders,
			Hashtable<String, Integer> rowColumnCount) {

		XSSFRow row = null;
		XSSFCell cell = null;
		for (int r = 0; r < rowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);

			if (row != null) {
				for (int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {
					cell = row.getCell(c);
					if (cell != null) {
						excelHeaders.put(cell.toString(), c);
					}
				}
				break;
			}
		}
		return excelHeaders;
	}
	public static HashedMap getTestData(String filePath, String workBook,
			String sheetName, String testCaseId) throws FileNotFoundException,
			IOException {
		XSSFRow row = null;
		XSSFCell cell = null;
		try {
			// Establish connection to work sheet
			
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(
					filePath));
			XSSFSheet sheet = wb.getSheet(sheetName);
			Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();
			excelrRowColumnCount = findRowColumnCount(sheet,
					excelrRowColumnCount);

			// function call to find excel header fields
			Hashtable<String, Integer> excelHeaders = new Hashtable<String, Integer>();
			excelHeaders = readExcelHeaders(sheet, excelHeaders,
					excelrRowColumnCount);
			HashedMap data = new HashedMap();
			ArrayList<String> header = new ArrayList<String>();
			ArrayList<String> matcher = new ArrayList<String>();

			// Get all header
			row = sheet.getRow(0);
			if (row != null) {
				for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {
					cell = sheet.getRow(0).getCell(c);
					if (cell != null) {
						String temp = convertXSSFCellToString(row.getCell(c));
						header.add(temp);
					}
				}
			}

			// Get test data set
			for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
				row = sheet.getRow(r);
				if (row != null) {
					XSSFCell tempCell = sheet.getRow(r).getCell(0);
					if (tempCell != null) {
						String tcID = convertXSSFCellToString(row.getCell(0));
						if (tcID.equalsIgnoreCase(testCaseId)) {
							matcher.add(tcID);
							for (int c = 1; c < excelrRowColumnCount
									.get("ColumnCount"); c++) {
								cell = sheet.getRow(r).getCell(c);
								String temp = convertXSSFCellToString(row
										.getCell(c));
								matcher.add(temp);
							}
						}
					}
				}
			}

			// Add all the test data to a Map
			for (int i = 0; i < matcher.size(); i++) {
				matcher.size();
				data.put(header.get(i), matcher.get(i));
			}
			wb.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
public static void writeTestData(String filePath, String workBook,
            String sheetName, String testCaseId, String time) throws FileNotFoundException,
            IOException {
      
      
      Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();
      int columnTime=-1;
      int row_time=-1;
      XSSFRow row = null;
      XSSFCell cell = null;
      ArrayList<String> header = new ArrayList<String>();
      FileInputStream fsIP= new FileInputStream(filePath); //Read the spreadsheet that needs to be updated
   
   XSSFWorkbook wb = new XSSFWorkbook(fsIP); //Access the workbook
     
   XSSFSheet worksheet = wb.getSheet(sheetName); //Access the worksheet, so that we can update / modify it.
   excelrRowColumnCount = findRowColumnCount(worksheet, excelrRowColumnCount); 
  
   
// Get all header
     row = worksheet.getRow(0);
     if (row != null) {
           for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {
                 cell = worksheet.getRow(0).getCell(c);
                 if (cell != null) {
                       
                        String temp = convertXSSFCellToString(row.getCell(c));
                       header.add(temp);
                       if(temp.equalsIgnoreCase("ACTUL_RESULT"))
                       {
                       columnTime=c;
                       }
                 }
           }
     }
   
   
   
// Get test data set
     for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
           row = worksheet.getRow(r);
           if (row != null) {
                 XSSFCell tempCell = worksheet.getRow(r).getCell(0);
                 if (tempCell != null) {
                       String tcID = convertXSSFCellToString(row.getCell(0));
                       if (tcID.equalsIgnoreCase(testCaseId)) {
                             System.out.println(row.getRowNum());
                             row_time=row.getRowNum();
                       }
                 }
           }
     }
   
 
   
   Cell cell1 = null; // declare a Cell object   
   try{
   cell1 = worksheet.getRow(row_time).getCell(columnTime);   // Access the second cell in second row to update the value    
   cell1.setCellValue(time);}
   catch(Exception e){
       cell1 = worksheet.getRow(row_time).createCell(columnTime);   // Access the second cell in second row to update the value    
       cell1.setCellValue(time);
   }
    fsIP.close(); //Close the InputStream  
   FileOutputStream output_file =new FileOutputStream(filePath);  //Open FileOutputStream to write updates
     
   wb.write(output_file); //write changes
   wb.close(); 
   output_file.close();  //close the stream  
 
   

}


}
