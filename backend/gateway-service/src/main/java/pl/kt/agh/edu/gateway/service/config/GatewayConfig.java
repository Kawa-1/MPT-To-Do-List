package pl.kt.agh.edu.gateway.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kt.agh.edu.commons.jwt.JwtResolver;
import pl.kt.agh.edu.gateway.service.filter.GatewayServiceFilter;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            GatewayServiceFilter gatewayServiceFilter) {
        return builder.routes()
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(gatewayServiceFilter.apply(
                                        new GatewayServiceFilter.Config())))
                        .uri("lb://user-service"))
                .route("car-service", r -> r.path("/car/**")
                        .filters(f -> f.dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(gatewayServiceFilter.apply(
                                        new GatewayServiceFilter.Config())))
                        .uri("lb://car-service"))
                .route("task-service", r -> r.path("/task/**")
                        .filters(f -> f.dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(gatewayServiceFilter.apply(
                                        new GatewayServiceFilter.Config())))
                        .uri("lb://task-service"))
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE"))
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    JwtResolver jwtResolver(@Value("${auth.jwt.secret}") String jwtSecret) {
        return new JwtResolver(jwtSecret);
    }

}
