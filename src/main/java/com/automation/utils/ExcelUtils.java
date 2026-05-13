package com.automation.utils;

import com.automation.model.TestCase;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;
import java.io.File;

public class ExcelUtils {

    public static void saveToExcel(
        List<TestCase> testCases, String storyId) {

        try {

            Workbook workbook =
                    new XSSFWorkbook();

            Sheet sheet =
                    workbook.createSheet(
                            "AI Test Cases");

            // Header Style
            CellStyle headerStyle =
                    workbook.createCellStyle();

            Font headerFont =
                    workbook.createFont();

            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            // Create Header Row
            Row headerRow =
                    sheet.createRow(0);

            String[] headers = {

                    "Test Case ID",
                    "Scenario",
                    "Preconditions",
                    "Test Steps",
                    "Expected Result",
                    "Priority",
                    "Severity",
                    "Test Type"
            };

            for(int i = 0; i < headers.length; i++) {

                Cell cell =
                        headerRow.createCell(i);

                cell.setCellValue(headers[i]);

                cell.setCellStyle(headerStyle);
            }

            // Insert Test Cases
            int rowNum = 1;

            for(TestCase tc : testCases) {

                Row row =
                        sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(
                                tc.getTestCaseId());

                row.createCell(1)
                        .setCellValue(
                                tc.getScenario());

                row.createCell(2)
                        .setCellValue(
                                tc.getPreconditions());

                row.createCell(3)
                        .setCellValue(
                                tc.getTestSteps());

                row.createCell(4)
                        .setCellValue(
                                tc.getExpectedResult());

                row.createCell(5)
                        .setCellValue(
                                tc.getPriority());

                row.createCell(6)
                        .setCellValue(
                                tc.getSeverity());

                row.createCell(7)
                        .setCellValue(
                                tc.getTestType());
            }

            // Auto Size Columns
            for(int i = 0; i < headers.length; i++) {

                sheet.autoSizeColumn(i);
            }

            // Save File
            File directory =
        new File("reports/ai-output");

if(!directory.exists()) {

    directory.mkdirs();
}

FileOutputStream fileOut =
        new FileOutputStream(
                "reports/ai-output/"
                + storyId
                + "_AI_TestCases.xlsx");

            workbook.write(fileOut);

            fileOut.close();
            workbook.close();

            System.out.println(
                    "Excel generated successfully.");

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}