package org.example.diplomski.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class StaticResourceConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry reg) {
            reg.addResourceHandler("/files/profile-pictures/**")
                    .addResourceLocations("file:uploads/profile-pictures/");
        }
    }