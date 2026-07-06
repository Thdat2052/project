package com.example.fileservice.services.pdfs.impls;

import com.example.common.service.exceptions.AppException;
import com.example.fileservice.services.pdfs.BaseExportPdfService;
import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl extends BaseExportPdfService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public InputStreamResource generatePdfFile(String templateName, Map<String, Object> data) throws AppException {
        try {
            Context context = new Context();
            context.setVariables(data);

            String htmlContent = templateEngine.process(templateName, context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(
                    this.getClass().getResource("/fonts/DejaVuSans.ttf").getPath(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(baos, false);
            renderer.finishPDF();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            return new InputStreamResource(bais);

        } catch (Exception e) {
            logger.error("Error generating PDF: {}", e.getMessage(), e);
            throw new AppException("Failed to generate PDF: " + e.getMessage());
        }
    }
}
