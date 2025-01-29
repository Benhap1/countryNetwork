package ru.skillbox.country.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${app.storage.accesKeyId}")
    private String accessKeyId;

    @Value("${app.storage.secretKey}")
    private String secretKey;

    @Value("${app.storage.bucketName}")
    private String bucketName;

    @Value("${app.storage.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKeyId, secretKey)
                .build();
    }
}
