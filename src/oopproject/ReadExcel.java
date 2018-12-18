/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

/**
 *
 * @author JustALemon
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Workbook;

public class ReadExcel {

  public double read(int startLine, int start, int stop) throws IOException {
    File excelFile = new File("TrainFee.xls");
    FileInputStream fis = new FileInputStream(excelFile);

    // we create an XSSF Workbook object for our XLSX Excel File
    Workbook workbook = new HSSFWorkbook(fis);
    // we get first sheet
    HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(startLine);
    HSSFRow row = sheet.getRow(start);
    HSSFCell cell = row.getCell(stop);
    String keep = cell.toString();
    double kept = Double.parseDouble(keep);
    workbook.close();
    fis.close();
    return kept;
  }
	
}
