package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.dao.mapper")
@EnableScheduling
public class BuptHotel2Application {

    public static void main(String[] args) {
        SpringApplication.run(BuptHotel2Application.class, args);
    }

}
