package ru.skillbox.country.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@Slf4j
public class AppConfiguration {

    @Value("${app.baseUrl.cityAndCountryApi}")
    private String baseUrlForRequestCountriesAndCities;

    @Value("${app.baseUrl.checkRequestToken}")
    private String baseUrlForCheckRequestToken;

    @Value("${app.storage.accesKeyId}")
    private String accessKeyId;

    @Value("${app.storage.secretKey}")
    private String accessKeySecret;

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(baseUrlForRequestCountriesAndCities)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(5000 * 1024))
                        .build())
                .build();
    }

    @Bean
    public WebClient checkTokenWebClient() {
        return WebClient
                .builder()
                .baseUrl(baseUrlForCheckRequestToken)
                .build();
    }

//    @Bean
//    public AmazonS3 s3Client() {
//        try {
//            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
//                    .withEndpointConfiguration(
//                            new AwsClientBuilder.EndpointConfiguration(
//                                    "https://storage.yandexcloud.net",
//                                    "ru-central1"
//                            )
//                    ).withCredentials
//                            (new AWSStaticCredentialsProvider(
//                                    new BasicAWSCredentials(accessKeyId, accessKeySecret)))
//                    .build();
//
//            return amazonS3;
//        } catch (SdkClientException e) {
//            log.error("Ошибка создания клиента для объекта хранения AWS SDK. Причина: {}", e.getMessage());
//            throw new SdkClientException((e.getMessage()));
//        }
//    }
}
