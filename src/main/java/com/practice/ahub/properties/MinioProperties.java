package com.practice.ahub.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String url;
    private String access_key;
    private String secret_key;
}
