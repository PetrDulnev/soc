package com.practice.ahub;

import com.practice.ahub.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(MinioProperties.class)
public class AhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhubApplication.class, args);
	}

}
