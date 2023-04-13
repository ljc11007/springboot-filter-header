package com.example.springbootfilterheader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootFilterHeaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFilterHeaderApplication.class, args);
    }

}
