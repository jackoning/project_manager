package com.sinyun.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author gongwenjun
 * @date 2020-03-15
 */
@EnableScheduling
@SpringBootApplication
public class ServserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServserManagerApplication.class);
    }
}
