package vju.finalexam.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer {

    @Bean
    public WebMvcConfigurer forwardToIndex() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("redirect:/ui/index.html");
                registry.addViewController("/ui").setViewName("redirect:/ui/index.html");
                registry.addViewController("/ui/").setViewName("redirect:/ui/index.html");
            }
        };
    }

}