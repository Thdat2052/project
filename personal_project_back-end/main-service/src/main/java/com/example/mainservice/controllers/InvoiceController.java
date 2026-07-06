package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.InvoiceInformationDetailModel;
import com.example.mainservice.models.InvoiceServiceInfoDetailModel;
import com.example.mainservice.services.InvoiceService;
import com.example.mainservice.services.UtilityIndexService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtils.INVOICE_URL)
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/calculate")
    public ResponseEntity<List<InvoiceInformationDetailModel>> createOrUpdate(@RequestParam("roomId") Long roomId,
                                                                              @RequestParam("month") Integer month,
                                                                              @RequestParam("year") Integer year)
            throws AppException {
        return ResponseEntity.ok(invoiceService.calculateInvoice(roomId, month, year));
    }
    @GetMapping("me")
    public ResponseEntity<List<InvoiceInformationDetailModel>> getMyInvoices()
            throws AppException {
        var data = invoiceService.getInvoiceInfoByTime(5, 2025);
        return ResponseEntity.ok(invoiceService.getMyInvoices());
    }
    @GetMapping("/all-by-time")
    public ResponseEntity<List<InvoiceServiceInfoDetailModel>> getInvoiceInfoByTime(@RequestParam("month") Integer month,
                                                                                    @RequestParam("year") Integer year)
            throws AppException {
        var data = invoiceService.getInvoiceInfoByTime(month, year);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdIn(@PathVariable("id") Long id) {
        invoiceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/confirm-payment")
    public ResponseEntity<Void> confirmPayment(@PathVariable("id") Long id) throws AppException {
        invoiceService.confirmPayment(id);
        return ResponseEntity.ok().build();
    }

}
