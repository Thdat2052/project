package com.example.fileservice.controllers.uploads;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.utils.UrlUtils;
import com.example.fileservice.models.FileUploadModel;
import com.example.fileservice.services.uploads.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(UrlUtils.FILE_URL)
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@ModelAttribute FileUploadModel fileUpload) throws IOException, AppException {
        return ResponseEntity.ok(fileService.upload(fileUpload));
    }
}
