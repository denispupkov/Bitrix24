package com.bitrix.utilities;

import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelUtil {

    private Sheet workSheet;
    private Workbook workBook;
    private String path;

    public ExcelUtil(String path, String sheetName) {
        this.path = path;
        try {
            FileInputStream ExcelFile = new FileInputStream(path);
            workBook = WorkbookFactory.create(ExcelFile);
            workSheet = workBook.getSheet(sheetName);
            Assert.assertNotNull(workSheet, "Sheet: \"" + sheetName + "\" does not exist\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            return cell.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getDataArray() {

        String[][] data = new String[rowCount()][columnCount()];

        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;
    }

    public List<Map<String, String>> getDataList() {

        List<String> columns = getColumnsNames();

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {

            Row row = workSheet.getRow(i);

            Map<String, String> rowMap = new HashMap<>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }

            data.add(rowMap);
        }

        return data;
    }

    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();

        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }

    public void setCellData(String value, int rowNum, int colNum) {
        Cell cell;
        Row row;

        try {
            row = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);

            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workBook.write(fileOut);

            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellData(String value, String columnName, int row) {
        int column = getColumnsNames().indexOf(columnName);
        setCellData(value, row, column);
    }

    public int columnCount() {
        return workSheet.getRow(0).getLastCellNum();
    }

    public int rowCount() {
        return workSheet.getPhysicalNumberOfRows();
    }
}
