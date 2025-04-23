package com.project.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 */
@SpringBootApplication  // 添加这个注解
@EnableJpaRepositories
@MapperScan("com.project.demo.dao")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(Application.class, args);
    }
}