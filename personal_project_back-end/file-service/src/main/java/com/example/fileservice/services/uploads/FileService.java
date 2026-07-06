package com.example.fileservice.services.uploads;

import com.example.common.service.exceptions.AppException;
import com.example.fileservice.models.FileUploadModel;

import java.io.IOException;

public interface FileService {
    String upload(FileUploadModel fileUpload) throws AppException, IOException;
}

