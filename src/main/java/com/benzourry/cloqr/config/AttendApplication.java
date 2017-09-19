package com.benzourry.cloqr.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.benzourry.cloqr"})
@EntityScan(basePackages = {"com.benzourry.cloqr.core.model"})
@EnableJpaRepositories(basePackages = "com.benzourry.cloqr.core.dao")
@EnableAsync

public class AttendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendApplication.class, args);
    }
}
