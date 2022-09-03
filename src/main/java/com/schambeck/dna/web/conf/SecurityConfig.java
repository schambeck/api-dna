package com.schambeck.dna.web.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Profile("!test")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().authorizeRequests()
                .antMatchers(GET, "/mutant/**").hasAuthority("SCOPE_read")
                .antMatchers(POST, "/mutant").hasAuthority("SCOPE_write")
                .anyRequest().permitAll()
                .and().oauth2ResourceServer().jwt();
    }

}
