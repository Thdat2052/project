package com.example.fileservice.services.excels;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public abstract class BaseExcelExportService<T> extends BaseFileService<T> {
    protected SXSSFWorkbook  workbook;
    protected SXSSFSheet sheet;

    protected abstract void writeHeader(Row headerRow);


    protected abstract void writeData(Sheet sheet, List<T> data);

    protected abstract void writeFooter(Sheet sheet, int rowIndex);

    protected abstract String getSheetName();

    @Override
    public InputStreamResource writeFile(List<T> data) throws IOException  {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            this.workbook = new SXSSFWorkbook(100);
            this.sheet = workbook.createSheet(getSheetName());
            this.sheet.trackAllColumnsForAutoSizing();

            Row headerRow = sheet.createRow(0);
            writeHeader(headerRow);
            writeData(sheet, data);

            if (shouldWriteFooter()) {
                writeFooter(sheet, data.size() + 1);
            }

            autoResizeColumns(sheet.getRow(0).getPhysicalNumberOfCells());

            workbook.write(baos);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            return new InputStreamResource(bais);
        }finally {
            workbook.close();
            workbook.dispose();
        }
    }


    protected boolean shouldWriteFooter() {
        return false;
    }

    protected void autoResizeColumns(int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    protected CellStyle getHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    protected CellStyle getNormalStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    protected CellStyle getDateStyle(String format) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat(format));
        return style;
    }

    protected CellStyle getDecimalStyle(String format) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(format));
        return style;
    }
}
