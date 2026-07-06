package com.example.fileservice.services.excels;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomUtilityUsageReportModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomUtilityUsageReportService extends BaseExcelExportService<RoomUtilityUsageReportModel> {
    @Override
    protected void writeHeader(Row headerRow) {
        String[] headers = {"Mã phòng", "Tên phòng","Chỉ số nước tiêu thụ", "Chỉ số điện tiêu thụ", "Tháng", "Năm"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderStyle());
        }
    }

    @Override
    protected void writeData(Sheet sheet, List<RoomUtilityUsageReportModel> models) {
        int rowIndex = 1;
        CellStyle dateStyle = getDateStyle("dd/MM/yyyy");
        for (RoomUtilityUsageReportModel data : models) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getRoomId());
            row.createCell(1).setCellValue(data.getRoomName());
            row.createCell(2).setCellValue(data.getTotalWaterUsage());
            row.createCell(3).setCellValue(data.getTotalElectricUsage());
            row.createCell(4).setCellValue(data.getMonthInventory());
            row.createCell(5).setCellValue(data.getYearInventory());
        }
    }

    @Override
    protected void writeFooter(Sheet sheet, int rowIndex) {
    }

    @Override
    protected String getSheetName() {
        return "Báo cáo tiêu thụ điện nước";
    }


}

