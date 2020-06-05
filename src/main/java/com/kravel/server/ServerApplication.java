package com.kravel.server;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml,"
            + "classpath:database.yml,"
            + "classpath:keys.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }



}
