package pl.kt.agh.edu.gateway.service.config;

import pl.kt.agh.edu.gateway.service.filter.GatewayServiceFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            GatewayServiceFilter gatewayServiceFilter) {
        return builder.routes()
                .route("user-service", r -> r.path("/user/**")
                        .filters(f ->
                                f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(gatewayServiceFilter.apply(
                                                new GatewayServiceFilter.Config())))
                        .uri("lb://user-service"))
                .route("auth-service", r -> r.path("/auth/**").uri("lb://auth-service"))
                .build();
    }
}
