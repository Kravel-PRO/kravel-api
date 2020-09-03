package com.kravel.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
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
