package com.emrerenjs.bitidea.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(
                        "/static/profilephotos/**",
                        "/static/temp/**",
                        "/static/post/**"
                )
                .addResourceLocations(
                        "file:/home/emre/uploads/images/profilephotos/",
                        "file:/home/emre/uploads/images/temp/",
                        "file:/home/emre/uploads/images/post/"
                );
    }


}
