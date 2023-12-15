package pl.kt.agh.edu.authentication.service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kt.agh.edu.commons.jwt.JwtResolver;

import java.util.Date;
import java.util.Map;

@Service
public class JwtResolverExtService extends JwtResolver {
    private final Long jwtExpirationMs;

    public JwtResolverExtService(
            @Value("${auth.jwt.secret}")
            String jwtSecretKey,
            @Value("${auth.jwt.expirationInMillis}")
            Long jwtExpirationMs) {
        super(jwtSecretKey);
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
