package dev.koshy.cityguide_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Loggable logger = new SimpleLogger("log.txt");
        logger.log("Application is Starting");

        SpringApplication.run(Application.class, args);
    }


    public static void listResources() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:**/*");
            for (Resource res : resources) {
                System.out.println(res.getURL().toString().contains("d25.json") ? res.getURL().toString() : "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // CORS Setup
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("http://localhost:*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            }
        };
    }
}
