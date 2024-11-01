package com.presnakov.hotelbooking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public record DatabaseProperties(String username,
                                 String password,
                                 String driverClassName,
                                 String url) {
}
