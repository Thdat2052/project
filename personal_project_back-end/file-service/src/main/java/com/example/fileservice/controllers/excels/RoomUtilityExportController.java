package com.example.fileservice.controllers.excels;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomRevenueReportModel;
import com.example.common.service.models.reports.RoomUtilityUsageReportModel;
import com.example.common.service.utils.UrlUtils;
import com.example.fileservice.services.excels.*;
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
public class RoomUtilityExportController {

    private final RoomUtilityUsageReportService service;

    @PostMapping(value = "/room-utilities/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> roomUtilitiesReport(@RequestBody List<RoomUtilityUsageReportModel> reportModels) throws IOException {

        InputStreamResource resource = service.writeFile(reportModels);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=room-utilities.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }
}

