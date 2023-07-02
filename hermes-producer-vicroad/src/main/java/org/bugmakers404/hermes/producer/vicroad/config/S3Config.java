package org.bugmakers404.hermes.producer.vicroad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

  ProfileCredentialsProvider profileCredentialsProvider = ProfileCredentialsProvider.create();

  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
        .credentialsProvider(profileCredentialsProvider)
        .region(Region.AP_SOUTHEAST_2) // Replace with your desired region
        .build();
  }
}
