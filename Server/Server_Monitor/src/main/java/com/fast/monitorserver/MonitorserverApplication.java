package com.fast.monitorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = { "com.fast.bpserver", "com.fast.monitorserver" })
@EntityScan(basePackages = {"com.fast.bpserver.entity", "com.fast.monitorserver.entity"})
public class MonitorserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorserverApplication.class, args);
	}

}
