package com.workFlow.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfigs(
        Server server

) {
    public record Server(
           @NotEmpty String name,
           @NotEmpty String url
    ){}
}
