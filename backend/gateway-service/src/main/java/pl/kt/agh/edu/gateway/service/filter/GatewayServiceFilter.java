package pl.kt.agh.edu.gateway.service.filter;

import pl.kt.agh.edu.common.exception.AuthenticationException;
import pl.kt.agh.edu.common.util.JwtUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayServiceFilter extends AbstractGatewayFilterFactory<GatewayServiceFilter.Config> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceFilter.class);
    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    public GatewayServiceFilter() {
        super(Config.class);
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
            try {
                JwtUtil.validateToken(authHeader, jwtSecret);
            } catch (Exception e) {
                LOGGER.warn("JWT validation failure.", e);
                throw new AuthenticationException("JWT validation failure", e);
            }
            return chain.filter(exchange);
        };
    }

    private boolean requiresAuthentication(Config config, String path) {
        return config.unauthorizedApi.stream()
                .noneMatch(unauthorizedPaths -> unauthorizedPaths.equals(path));
    }

    @NoArgsConstructor
    @Data
    public static class Config {
        private List<String> unauthorizedApi;
    }
}
