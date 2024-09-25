package uz.resalsoft.yanascormdanimaqilayoxshamayapti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Barcha yo'llarga ruxsat
                .allowedOrigins("http://localhost:8000", "http://localhost:5173", "https://scorm-api.lazyprogrammer.uz", "https://malaka-oshirish.uz")// Frontend domeningizni kiriting
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Ruxsat berilgan metodlar
                .allowedHeaders("*") // Hamma headerlarga ruxsat
                .allowCredentials(true); // Cookie va auth ma'lumotlariga ruxsat
    }
}