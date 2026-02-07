package net.kanth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
@Configuration
@EnableWebFluxSecurity
public class ConfigApiSecurity {

	/*
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/users/**").permitAll()   // login/register
                        .anyExchange().authenticated()
                )
                .build();
    }
    */
    
    
	 @Bean
	 SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

	        return http
	                .csrf(ServerHttpSecurity.CsrfSpec::disable)
	                .authorizeExchange(exchange -> exchange
	                        .pathMatchers(
	                                "/api/users/**",          // login/register
	                                "/swagger-ui/**",         // swagger ui
	                                "/v3/api-docs/**",        // openapi docs
	                                "/actuator/**"            // health check
	                        ).permitAll()
	                        .anyExchange().permitAll()
	                )
	                .build();
	    }
}