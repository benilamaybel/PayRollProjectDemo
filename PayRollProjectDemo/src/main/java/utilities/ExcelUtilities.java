package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	static FileInputStream f;
	static XSSFWorkbook book;
	static XSSFSheet sheet;
	static String fileName;
/*
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
*/
	public List<String> getDataFromExcel(String fileName, String sheetName) throws IOException {
		f = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Excel\\" + fileName);
		XSSFWorkbook wb = new XSSFWorkbook(f);
		XSSFSheet sheet = wb.getSheet(sheetName);
		Iterator<Row> itr = sheet.iterator();
		List<String> list = new ArrayList<String>();

		while (itr.hasNext()) {
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				HSSFDataFormatter hdf = new HSSFDataFormatter();
				list.add(hdf.formatCellValue(cell));		
			}
		}
		return list;
	}

}
