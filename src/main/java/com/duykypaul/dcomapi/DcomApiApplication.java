package com.duykypaul.dcomapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class DcomApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcomApiApplication.class, args);
    }
}
