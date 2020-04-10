package com.jellyfishmix.wxinterchange;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JellyfishMIX
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jellyfishmix.wxinterchange.dao")
public class WxInterchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxInterchangeApplication.class, args);
    }

}
