package com.schedulingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.schedulingSystem")
public class SchedulingSystemApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SchedulingSystemApplication.class, args);
    }
}
