package com.forsale.mini.dao.file;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 设置虚拟路径，访问绝对路径下资源
 */
@Configuration
@EnableWebMvc
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class UploadConfig implements WebMvcConfigurer{
    //上传文件
    @Value("${file.uploadDir}") //@Value注解读取配置文件中reggie的值
    private String uploadDir;
    /**
     * 图片虚拟地址映射
     * @param registry
     * 设置该映射之后，外网只能访问本地的images文件内部的资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir);
    }

    /**
     * 文件上传需要添加
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
}