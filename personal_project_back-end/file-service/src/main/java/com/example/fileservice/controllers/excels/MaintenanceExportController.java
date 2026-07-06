package com.example.fileservice.controllers.excels;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomRevenueReportModel;
import com.example.common.service.utils.UrlUtils;
import com.example.fileservice.services.excels.MaintenanceExportService;
import com.example.fileservice.services.excels.RevenueByMonthExportService;
import com.example.fileservice.services.excels.RevenueByQuarterExportService;
import com.example.fileservice.services.excels.RevenueByYearExportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UrlUtils.FILE_URL)
@AllArgsConstructor
public class MaintenanceExportController {

    private final MaintenanceExportService service;

    @PostMapping(value = "/maintenance/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> maintenanceReport(@RequestBody List<MaintenanceStatusReportModel> reportModels) throws IOException {

        InputStreamResource resource = service.writeFile(reportModels);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=maintenance.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }
}

