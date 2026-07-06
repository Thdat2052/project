package com.example.mainservice.controllers.reports;

import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(UrlUtils.REPORT_URL)
@RestController
public class RevenueReportController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/revenue/month") // thống kê doanh thu theo tháng
    public ResponseEntity<List<RevenueStatResponse>> revenueByMonth(@RequestParam("month") int month, @RequestParam("year") int year ) {
        var data = invoiceService.inventoryByMonth(month, year);
        var datas = data!=null ? List.of(data) : new ArrayList<RevenueStatResponse>();
        return ResponseEntity.ok(datas);
    }

    @GetMapping("/revenue/quarter")// thống kê doanh thu theo quý
    public ResponseEntity<List<RevenueStatResponse>> revenueByQuarter(@RequestParam("quarter") int quarter, @RequestParam("year") int year ) {
        var data = invoiceService.inventoryByQuarter(quarter, year);
        var datas = data!=null ? List.of(data) : new ArrayList<RevenueStatResponse>();
        return ResponseEntity.ok(datas);
    }

    @GetMapping("/revenue/year")// thống kê doanh thu theo năm
    public ResponseEntity<List<RevenueStatResponse>> revenueByYear(@RequestParam("year") int year ) {
        var data = invoiceService.inventoryByYear(year);
        var datas = data!=null ? List.of(data) : new ArrayList<RevenueStatResponse>();
        return ResponseEntity.ok(datas);
    }

}
