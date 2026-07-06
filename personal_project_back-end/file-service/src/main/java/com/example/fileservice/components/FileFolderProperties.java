package com.example.fileservice.components;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "file.folder")
public class FileFolderProperties {
    private String upload;
    private String template;
    private String generate;
}
