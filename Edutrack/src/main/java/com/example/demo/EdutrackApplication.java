package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.security.SecurityConfig;

import java.util.logging.Logger;
@SpringBootApplication
public class EdutrackApplication {
	
	private static final Logger log = Logger.getLogger(EdutrackApplication.class.getName());
	
	
	public static void main(String[] args) {
		SpringApplication.run(EdutrackApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SecurityConfig.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Cambia esto al origen de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
			}
		};
	}

}
