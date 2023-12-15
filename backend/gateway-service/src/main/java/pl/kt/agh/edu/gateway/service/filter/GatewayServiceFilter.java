package pl.kt.agh.edu.gateway.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import pl.kt.agh.edu.commons.jwt.JwtResolver;

import java.util.List;

@Component
public class GatewayServiceFilter extends AbstractGatewayFilterFactory<GatewayServiceFilter.Config> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceFilter.class);
    private final JwtResolver jwtResolver;

    public GatewayServiceFilter(JwtResolver jwtResolver) {
        super(Config.class);
        this.jwtResolver = jwtResolver;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            LOGGER.info("Requested URI requires authentication. URI: {}", exchange.getRequest().getURI());
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new AuthenticationException("Authorization header not found");
            }

            List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new AuthenticationException("Authorization header is empty");
            }
            String authHeader = authHeaders.get(0);
            LOGGER.info("Auth header: {}", authHeader);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            if (!jwtResolver.isTokenValid(authHeader)) {
                LOGGER.warn("JWT token is expired!");
                throw new AuthenticationException("JWT token is expired");
            }
            return chain.filter(exchange);
        };
    }

    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }

    public static class Config {
    }
}
