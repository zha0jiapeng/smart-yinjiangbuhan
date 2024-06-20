package com.rubin.rpan.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rubin")
public class RPanLaunch {

    public static void main(String[] args) {
        SpringApplication.run(RPanLaunch.class, args);
    }

}
