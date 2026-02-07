package net.kanth.authservice;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private final JwtServiceGateway jwtService;
    private final RateLimitSlidingWindowService  rateLimiter;

    public JwtAuthFilter(JwtServiceGateway jwtService,RateLimitSlidingWindowService  rateLimiter) {
        this.jwtService = jwtService;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Skip authentication for these paths
        if (path.startsWith("/api/users") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/actuator")) {

            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String userId = jwtService.extractUserId(token);
 
        if(userId == null){
        	   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        	   return exchange.getResponse().setComplete();
        }
        
        // ✅ Rate Limit Check (Example: 5 requests in 10 seconds)
        return rateLimiter.isAllowed(userId, 5, 10)
                .flatMap(allowed -> {

                    if (!allowed) {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                    
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(exchange.getRequest().mutate()
                                    .header("X-USER-ID", userId)
                                    .build())
                            .build();

                    return chain.filter(modifiedExchange);
                });
    }
}