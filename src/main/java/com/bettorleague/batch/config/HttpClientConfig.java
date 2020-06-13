package com.bettorleague.batch.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfig {
    private final HttpInterceptor httpInterceptor;

    public HttpClientConfig(HttpInterceptor httpInterceptor){
        this.httpInterceptor = httpInterceptor;
    }

    @Bean
    public RestTemplate httpClient(RestTemplateBuilder builder) {
        return builder.additionalInterceptors(httpInterceptor).build();
    }
}
