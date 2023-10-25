package com.banque.api.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGateWayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/cs/**")
                        .uri("lb://client-service"))
                .route(p -> p.path("/api/ts/**")
                        .uri("lb://transaction-service"))
                .route(p -> p.path("/users")
                       .uri("lb://api-gateway"))
                .build();
    }
}
