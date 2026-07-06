package com.example.fileservice.controllers.pdfs;

import com.example.common.service.exceptions.AppException;
import com.example.common.service.utils.UrlUtils;
import com.example.fileservice.models.request.InvoiceRequest;
import com.example.fileservice.services.pdfs.BaseExportPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(UrlUtils.FILE_URL)
public class InvoiceController {

    @Autowired
    private BaseExportPdfService pdfGenerateService;

    @PostMapping(value = "/bill/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateElectricBill(@RequestBody InvoiceRequest invoiceRequest) throws AppException, IOException {

        Map<String, Object> data = new HashMap<>();
        data.put("detail", invoiceRequest.getDetail());
        data.put("invoiceInformation", invoiceRequest.getInvoiceInformation());
        data.put("serviceOfRooms", invoiceRequest.getServiceOfRooms());
        data.put("serviceMaintenanceOfRooms", invoiceRequest.getServiceMaintenanceOfRooms());
        data.put("totalMoney", invoiceRequest.getTotalMoney());
        InputStreamResource resource = pdfGenerateService.generatePdfFile("bill", data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=electric_bill.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}