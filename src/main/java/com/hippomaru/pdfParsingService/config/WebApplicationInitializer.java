package com.hippomaru.pdfParsingService.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String tempDir = "C:\\Users\\вероника\\IdeaProjects\\pdf-parsing-service2\\temp";  // Для Windows

        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                tempDir,
                10_485_760,     // 10MB
                21_474_836,     // 20MB
                4096            // 4KB
        );

        registration.setMultipartConfig(multipartConfig);
    }
    @Override
    protected Class<?>[] getRootConfigClasses()  {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}