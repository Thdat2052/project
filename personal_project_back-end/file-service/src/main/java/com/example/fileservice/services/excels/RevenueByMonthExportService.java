package com.example.fileservice.services.excels;

import com.example.fileservice.entities.Employee;
import com.example.common.service.models.reports.RoomRevenueReportModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueByMonthExportService extends BaseExcelExportService<RoomRevenueReportModel> {
    @Override
    protected void writeHeader(Row headerRow) {
        String[] headers = {"Tháng", "Năm", "Doanh thu"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderStyle());
        }
    }

    @Override
    protected void writeData(Sheet sheet, List<RoomRevenueReportModel> revenueReportModels) {
        int rowIndex = 1;
        CellStyle decimalStyle = getDecimalStyle("#,##0.00");

        for (RoomRevenueReportModel data : revenueReportModels) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getMonth());
            row.createCell(1).setCellValue(data.getYear());

            Cell totalRevenueCell = row.createCell(2);
            totalRevenueCell.setCellValue(data.getTotalRevenue().doubleValue());
            totalRevenueCell.setCellStyle(decimalStyle);
        }
    }

    @Override
    protected void writeFooter(Sheet sheet, int rowIndex) {
    }

    @Override
    protected String getSheetName() {
        return "Báo cáo doanh thu theo tháng";
    }


}

