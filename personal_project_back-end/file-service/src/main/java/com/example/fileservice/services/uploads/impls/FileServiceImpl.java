package com.example.fileservice.services.uploads.impls;

import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.ErrorModel;
import com.example.common.service.utils.FileUtils;
import com.example.common.service.utils.StringUtils;
import com.example.fileservice.components.FileFolderProperties;
import com.example.fileservice.constants.ExceptionMessageConstant;
import com.example.fileservice.constants.FileFolderConstant;
import com.example.fileservice.models.FileUploadModel;
import com.example.fileservice.services.uploads.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private FileFolderProperties fileFolderProperties;


    @Override
    public String upload(FileUploadModel fileUpload) throws AppException, IOException {

        if (fileUpload.getFile().isEmpty()) throw AppException.of(ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ExceptionMessageConstant.FILE_EMPTY));

        String originPath = null;
        String nameFile = StringUtils.formatFileName(fileUpload.getFile().getOriginalFilename());
        switch (fileUpload.getFileUploadType()) {
            case CATEGORY:
                originPath = fileFolderProperties.getUpload() + FileFolderConstant.prefix + FileFolderConstant.CATEGORY_UPLOAD_FOLDER;
                break;
            case USER:
                originPath = fileFolderProperties.getUpload() + FileFolderConstant.prefix + FileFolderConstant.USER_UPLOAD_FOLDER;
                break;
            default:
                throw new IllegalArgumentException("Unsupported entity type: " + fileUpload.getFileUploadType());
        }

        FileUtils.upload(fileUpload.getFile(), originPath, nameFile);
        return nameFile;
    }

}
