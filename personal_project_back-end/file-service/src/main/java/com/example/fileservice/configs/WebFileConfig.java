package com.example.fileservice.configs;

import com.example.fileservice.components.FileFolderProperties;
import com.example.fileservice.constants.FileFolderConstant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebFileConfig implements WebMvcConfigurer {
    private FileFolderProperties fileFolderProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/user"  + "/**")
                .addResourceLocations("file:" + fileFolderProperties.getUpload() + FileFolderConstant.prefix + FileFolderConstant.USER_UPLOAD_FOLDER);
    }

}