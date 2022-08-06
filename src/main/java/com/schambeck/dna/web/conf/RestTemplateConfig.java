package com.schambeck.dna.web.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class RestTemplateConfig {

    @Value("${custom.api-notification.base-url}")
    private String baseUrl;

    @Bean("apiNotification")
    @RequestScope
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken oauthToken) {
            return builder.uriTemplateHandler(new DefaultUriBuilderFactory(baseUrl))
                    .interceptors(getBearerTokenInterceptor(oauthToken.getToken().getTokenValue()))
                    .build();
        }
        throw new IllegalStateException("Can't access the api-notification without an access token");
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "Bearer ".concat(accessToken));
            return execution.execute(request, bytes);
        };
    }

}
