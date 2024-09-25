package uz.resalsoft.yanascormdanimaqilayoxshamayapti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
/*
@CrossOrigin(origins = {"http://localhost:8000", "http://localhost:5173"})
*/
public class YanaScormdaNimaQilayOxshamayaptiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YanaScormdaNimaQilayOxshamayaptiApplication.class, args);
    }
}
