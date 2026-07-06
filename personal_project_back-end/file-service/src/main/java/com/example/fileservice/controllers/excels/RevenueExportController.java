package com.example.fileservice.controllers.excels;

import com.example.common.service.models.reports.RoomRevenueReportModel;
import com.example.common.service.utils.UrlUtils;
import com.example.fileservice.services.excels.RevenueByMonthExportService;
import com.example.fileservice.services.excels.RevenueByQuarterExportService;
import com.example.fileservice.services.excels.RevenueByYearExportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UrlUtils.FILE_URL)
@AllArgsConstructor
public class RevenueExportController {

    private final RevenueByMonthExportService revenueByMonthExportService;
    private final RevenueByQuarterExportService revenueByQuarterExportService;
    private final RevenueByYearExportService revenueByYearExportService;

    @PostMapping(value = "/month/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> revenueByMonth(@RequestBody List<RoomRevenueReportModel> reportModels) throws IOException {

        InputStreamResource resource = revenueByMonthExportService.writeFile(reportModels);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=revenue_month.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    @PostMapping(value = "/quarter/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> revenueByQuarter(@RequestBody List<RoomRevenueReportModel> reportModels) throws IOException {

        InputStreamResource resource = revenueByQuarterExportService.writeFile(reportModels);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=revenue_quarter.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }


    @PostMapping(value = "/year/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> revenueByYear(@RequestBody List<RoomRevenueReportModel> reportModels) throws IOException {

        InputStreamResource resource = revenueByYearExportService.writeFile(reportModels);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=revenue_year.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }
}

