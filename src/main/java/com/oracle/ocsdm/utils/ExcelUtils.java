package com.oracle.ocsdm.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static FileInputStream fileInputStream;
    public static FileOutputStream fileOutputStream;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;

/*    public static int getRowCount(String fileName, String sheetName) throws IOException {
        fileInputStream = new FileInputStream(fileName);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum();
        workbook.close();
        fileInputStream.close();
        return rowcount;
    }

    public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
        fileInputStream = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        int cellcount = row.getLastCellNum();
        workbook.close();
        fileInputStream.close();
        return cellcount;
    }

    public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
        fileInputStream = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);
        String data;
        try {
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        } catch (Exception e) {
            data = "";
        }
        workbook.close();
        fileInputStream.close();
        return data;
    }

    public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data) throws IOException {
        fileInputStream = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue(data);
        fileOutputStream = new FileOutputStream(xlfile);
        workbook.write(fileOutputStream);
        workbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }*/
    public String[][] getExcelData(String fileName) throws IOException {
        String[][] data = null;
        System.out.println(data);
        FileInputStream fis = new FileInputStream(fileName);
        System.out.println(fis);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        System.out.println(workbook);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            System.out.println(sheet);
            XSSFRow row = sheet.getRow(0);
            System.out.println(row);
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println(numberOfRows);
            int numOfColumns = row.getLastCellNum();
            System.out.println(numOfColumns);
            Cell cell;
            data = new String[numberOfRows - 1][numOfColumns];
            {
                for (int r = 1; r < numberOfRows; r++) {
                    for (int c = 0; c < numOfColumns; c++) {
                        row = sheet.getRow(r);
                        cell = row.getCell(c);
                        data[r - 1][c] = cell.getStringCellValue();
                    }
                }

            }
        }
        return data;

    }
}






