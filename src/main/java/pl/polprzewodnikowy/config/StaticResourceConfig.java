package pl.polprzewodnikowy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/images/**").addResourceLocations("file:resources/images/");
        registry.addResourceHandler("/resources/css/**").addResourceLocations("file:resources/css/");
        registry.addResourceHandler("/resources/js/**").addResourceLocations("file:resources/js/");
    }

}