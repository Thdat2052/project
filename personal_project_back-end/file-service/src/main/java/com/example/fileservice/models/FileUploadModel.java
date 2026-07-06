package com.example.fileservice.models;

import com.example.fileservice.enums.FileUploadTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadModel {
    private MultipartFile file;
    private FileUploadTypeEnum fileUploadType;
}
