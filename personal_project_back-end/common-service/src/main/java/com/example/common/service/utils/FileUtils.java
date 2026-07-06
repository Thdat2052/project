package com.example.common.service.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static String upload(MultipartFile file, String pathFolder, String nameFile) throws IOException {
        Path folderPath = Paths.get(pathFolder);

        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        Path filepath = Paths.get(pathFolder, nameFile);
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return nameFile;
    }
}
