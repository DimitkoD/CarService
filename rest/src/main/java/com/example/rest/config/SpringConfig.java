package com.example.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class SpringConfig {
    private final Environment env;

    public SpringConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource mainDB(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource1.driverClassName")));
        dataSource.setUrl(env.getProperty("spring.datasource1.url"));
        dataSource.setUsername(env.getProperty("spring.datasource1.username"));
        dataSource.setPassword(env.getProperty("spring.datasource1.password"));

        return dataSource;
    }
}
