package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	static FileInputStream f;
	static XSSFWorkbook book;
	static XSSFSheet sheet;
	static String fileName;

	public String getStringData(String fileName, String sheetName, int row, int cell) throws IOException {
		f = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Excel\\" + fileName);
		book = new XSSFWorkbook(f);
		sheet = book.getSheet(sheetName);
		Row r = sheet.getRow(row);
		Cell c = r.getCell(cell);
		return c.getStringCellValue();
	}

	public int getIntegerData(String fileName, String sheetName, int row, int cell) throws IOException {
		f = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Excel\\" + fileName);
		book = new XSSFWorkbook(f);
		sheet = book.getSheet(sheetName);
		Row r = sheet.getRow(row);
		Cell c = r.getCell(cell);
		return (int) c.getNumericCellValue();
	}

	/*
	 * public void getData() throws IOException { f = new
	 * FileInputStream("D:\\benila\\LoginCredentials.xlsx"); XSSFWorkbook wb = new
	 * XSSFWorkbook(f); XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet
	 * object to retrieve object Iterator<Row> itr = sheet.iterator(); // iterating
	 * over excel file while (itr.hasNext()) { Row row = itr.next(); Iterator<Cell>
	 * cellIterator = row.cellIterator(); // iterating over each column while
	 * (cellIterator.hasNext()) { Cell cell = cellIterator.next();
	 * 
	 * System.out.print(cell.getStringCellValue() + "\t"); } }
	 * 
	 * }
	 */

}
