package com.krabelard.safeapp;

import com.krabelard.safeapp.security.properties.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SafeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeAppApplication.class, args);
    }

}
