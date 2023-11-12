package pl.kt.agh.edu.authentication.service.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "auth.jwt")
@Data
@Component
@NoArgsConstructor
public class AuthenticationConfigProps {
    private String secret;
    private long expirationInMillis;
}
