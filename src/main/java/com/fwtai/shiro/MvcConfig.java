package com.fwtai.shiro;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//ajax客户端不需要设置 xhrFields : {withCredentials: true},crossDomain : true,
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("*")
            .allowedHeaders("*")
            .maxAge(3600L);
    }
}