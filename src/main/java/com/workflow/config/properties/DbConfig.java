package com.workflow.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class DbConfig {

    private Master master;
    private Slave slave;

    @Getter
    @Setter
    public static class Master {
        private String url;
        private String username;
        private String password;
        private String driverClassName;

    }

    @Getter
    @Setter
    public static class Slave {
        private String url;
        private String username;
        private String password;
        private String driverClassName;

    }

}
