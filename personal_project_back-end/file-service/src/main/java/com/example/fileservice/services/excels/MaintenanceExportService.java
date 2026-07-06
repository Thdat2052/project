package com.example.fileservice.services.excels;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomRevenueReportModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceExportService extends BaseExcelExportService<MaintenanceStatusReportModel> {
    @Override
    protected void writeHeader(Row headerRow) {
        String[] headers = {"Mã phòng", "Tên phòng","Mã yêu cầu", "Ngày yêu cầu", "Trạng thái hoàn thành"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderStyle());
        }
    }

    @Override
    protected void writeData(Sheet sheet, List<MaintenanceStatusReportModel> models) {
        int rowIndex = 1;
        CellStyle dateStyle = getDateStyle("dd/MM/yyyy");
        for (MaintenanceStatusReportModel data : models) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getRoomId());
            row.createCell(1).setCellValue(data.getRoomName());
            row.createCell(2).setCellValue(data.getMaintenanceId());
            Cell dateCell = row.createCell(3);
            dateCell.setCellValue(data.getRequestDate());
            dateCell.setCellStyle(dateStyle);
            row.createCell(4).setCellValue(data.getMaintenanceStatus());
        }
    }

    @Override
    protected void writeFooter(Sheet sheet, int rowIndex) {
    }

    @Override
    protected String getSheetName() {
        return "Báo cáo tình trạng bảo trì thiết bị";
    }


}

