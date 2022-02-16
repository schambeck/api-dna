package com.schambeck.dna.web.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class RestTemplateConfig {

    @Value("${custom.api-notification.base-url}")
    private String baseUrl;

    @Bean
    RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(baseUrl)).build();
    }

}
