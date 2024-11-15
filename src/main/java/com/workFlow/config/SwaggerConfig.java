package com.workFlow.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    private final AppConfigs appConfigs;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My API")
                        .description("API for My Workflow Application")
                        .version("1.0")
                        .contact(new Contact().name("Support").email("nitesh6g@gmail.com").url("https://gmail.com")))
                .addServersItem(new Server().url(appConfigs.server().url()).description(appConfigs.server().name()));
    }
}
