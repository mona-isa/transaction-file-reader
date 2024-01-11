package com.company.bank.transaction.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {
    private String region;
    private S3Config s3;

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider credentialsProvider = AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(DefaultCredentialsProvider.create())
                .build();

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public String bucketName() {
        return s3.bucketName;
    }

    @Setter
    @Getter
    public static class S3Config {
        private String bucketName;
    }
}
