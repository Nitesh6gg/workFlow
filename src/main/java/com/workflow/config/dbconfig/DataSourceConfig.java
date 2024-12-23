package com.workflow.config.dbconfig;

import com.workflow.config.properties.DbConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class DataSourceConfig {

    private final DbConfig dbConfig;

    @Bean
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .url(dbConfig.getMaster().getUrl())
                .username(dbConfig.getMaster().getUsername())
                .password(dbConfig.getMaster().getPassword())
                .driverClassName(dbConfig.getMaster().getDriverClassName())
                .build();
    }

    @Bean
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .url(dbConfig.getSlave().getUrl())
                .username(dbConfig.getSlave().getUsername())
                .password(dbConfig.getSlave().getPassword())
                .driverClassName(dbConfig.getSlave().getDriverClassName())
                .build();
    }

    @Bean
    public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DatabaseContextHolder.MASTER, masterDataSource);
        dataSources.put(DatabaseContextHolder.SLAVE, slaveDataSource);

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DatabaseContextHolder.getDatabaseType();
            }
        };
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }
}
