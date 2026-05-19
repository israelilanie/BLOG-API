package com.example.blogapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(
                 new Info().title("Blog API")
                         .version("1.0")
                         .description("Backend API for Blog system with Users, Posts, Comments")
                );
    }
}
