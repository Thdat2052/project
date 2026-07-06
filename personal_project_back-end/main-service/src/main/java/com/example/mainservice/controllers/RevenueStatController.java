package com.example.mainservice.controllers;

import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlUtils.INVENTORY_URL)
public class RevenueStatController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/revenue") // thống kê doanh thu tổng
    public ResponseEntity<List<RevenueStatResponse>> getRevenue() {
        return ResponseEntity.ok(invoiceService.inventory());
    }
    @GetMapping("/revenue/month") // thống kê doạnh thu theo tháng
    public ResponseEntity<RevenueStatResponse> getRevenueByMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        return ResponseEntity.ok(invoiceService.inventoryByMonth(month, year));
    }

    @GetMapping("/revenue/quarter") // thống kê doạnh thu theo quý
    public ResponseEntity<RevenueStatResponse> getRevenueByQuarter(@RequestParam("year") int year, @RequestParam("quarter") int quarter) {
        return  ResponseEntity.ok(invoiceService.inventoryByQuarter(quarter, year));
    }

    @GetMapping("/revenue/year")// thống kê doạnh thu theo năm
    public ResponseEntity<RevenueStatResponse> getRevenueByYear(@RequestParam("year") int year) {
        return ResponseEntity.ok(invoiceService.inventoryByYear(year));
    }
}
