package com.example.fileservice.services.pdfs;


import com.example.common.service.exceptions.AppException;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public abstract class BaseExportPdfService {

    public abstract InputStreamResource generatePdfFile(String templateName, Map<String, Object> data)throws AppException;
}
