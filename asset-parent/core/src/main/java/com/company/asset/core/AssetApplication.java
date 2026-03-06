package com.company.asset.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Asset Management System Application
 */
@SpringBootApplication(scanBasePackages = "com.company.asset")
@MapperScan("com.company.asset.**.mapper")
@EnableTransactionManagement
public class AssetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
        System.out.println("Asset Management System Started Successfully!");
    }
}
