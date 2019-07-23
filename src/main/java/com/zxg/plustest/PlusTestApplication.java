package com.zxg.plustest;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zxg.plustest.mapper")
public class PlusTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusTestApplication.class, args);
    }

}
