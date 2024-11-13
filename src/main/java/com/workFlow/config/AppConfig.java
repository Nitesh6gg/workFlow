package com.workFlow.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Server server;

    @Getter
    @Setter
    public static class Server{
        @NotBlank
        private String name;
        @NotBlank
        private String url;

    }


}
