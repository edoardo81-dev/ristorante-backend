package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        // 👇 Elenca esplicitamente le origin del tuo frontend (niente "*")
        .allowedOriginPatterns(
            "http://localhost:4200",
            "http://127.0.0.1:4200"
        )
        .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)   // ora è valido perché NON usiamo "*"
        .maxAge(3600);
  }
}
