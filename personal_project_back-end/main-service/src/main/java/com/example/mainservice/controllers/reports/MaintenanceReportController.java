package com.example.mainservice.controllers.reports;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomUtilityUsageReportModel;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.repositories.ReportRepository;
import com.example.mainservice.services.InvoiceService;
import com.example.mainservice.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(UrlUtils.REPORT_URL)
@RestController
public class MaintenanceReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/maintenance") // báo cáo tình trạng bảo tri thiết bị
    public ResponseEntity<List<MaintenanceStatusReportModel>> getRoomUtilityUsageReport(@RequestParam("startDate") String startDate,
                                                                                        @RequestParam("endDate") String endDate ) {
        var datas = reportService.findMaintenanceStatusReport(startDate, endDate);
        return ResponseEntity.ok(datas);
    }
}
