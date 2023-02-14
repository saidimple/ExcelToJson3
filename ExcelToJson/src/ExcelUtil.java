
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
 
/**
 * 
 * @author javacodepoint.com
 *
 */
public class ExcelUtil {
 
 
    /**
     * Method to convert excel sheet data to JSON format
     * 
     * @param excel
     * @return
     */
    public static JSONObject excelToJson(File excel) {
        // hold the excel data sheet wise
        JSONObject excelData = new JSONObject();
        Workbook workbook = null;
        FileInputStream fis = null;
        try {
         // Creating file input stream
            fis = new FileInputStream(excel);
 
            String filename = excel.getName().toLowerCase();
            if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
                // creating workbook object based on excel file format
                if (filename.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fis);
                } else {
                    workbook = new XSSFWorkbook(fis);
                }
 
                // Reading each sheet one by one
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    String sheetName = sheet.getSheetName();
 
                    List<String> headers = new ArrayList<String>();
                    JSONArray sheetData = new JSONArray();
                    // Reading each row of the sheet
                    for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                        Row row = sheet.getRow(j);
                        if (j == 0) {
                            // reading sheet header's name
                            for (int k = 0; k < row.getLastCellNum(); k++) {
                                headers.add(row.getCell(k).getStringCellValue());
                            }
                        } else {
                            // reading work sheet data
                            JSONObject rowData = new JSONObject();
                            for (int k = 0; k < headers.size(); k++) {
                                Cell cell = row.getCell(k);
                                String headerName = headers.get(k);
                                if (cell != null) {
                                    switch (cell.getCellType()) {
                                    case FORMULA:
                                        rowData.put(headerName, cell.getCellFormula());
                                        break;
                                    case BOOLEAN:
                                        rowData.put(headerName, cell.getBooleanCellValue());
                                        break;
                                    case NUMERIC:
                                        rowData.put(headerName, cell.getNumericCellValue());
                                        break;
                                    case BLANK:
                                        rowData.put(headerName, "");
                                        break;
                                    default:
                                        rowData.put(headerName, cell.getStringCellValue());
                                        break;
                                    }
                                } else {
                                    rowData.put(headerName, "");
                                }
                            }
                            sheetData.put(rowData);
                        }
                    }
                    excelData.put(sheetName, sheetData);
                }
                return excelData;
            } else {
                throw new IllegalArgumentException("File format not supported.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
        }
        return null;
    }
}