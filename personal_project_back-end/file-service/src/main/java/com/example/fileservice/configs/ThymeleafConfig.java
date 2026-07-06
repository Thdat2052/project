package com.example.fileservice.configs;
import com.example.fileservice.components.FileFolderProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
@AllArgsConstructor
public class ThymeleafConfig {
    private FileFolderProperties fileFolderProperties;
    @Bean
    public FileTemplateResolver templateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(fileFolderProperties.getTemplate()+"/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }
}