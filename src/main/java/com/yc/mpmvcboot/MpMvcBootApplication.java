package com.yc.mpmvcboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@MapperScan(value = {"com.yc.mpmvcboot.mapper"})
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class MpMvcBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpMvcBootApplication.class, args);
    }

}
