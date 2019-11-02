package com.shl;

import com.shl.controller.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.shl.controller.RequestProcessingTimeInterceptor;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestProcessingTimeInterceptor()).addPathPatterns("/admin");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
