package pl.kt.agh.edu.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String createToken(String userName,
                                     Map<String, Object> claims,
                                     long expirationTimeMillis,
                                     String secret) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(getSignKey(secret), SignatureAlgorithm.HS256).compact();
    }

    public static void validateToken(final String token, final String secret) {
        Jwts.parserBuilder().setSigningKey(getSignKey(secret)).build().parseClaimsJws(token);
    }

    public static Key getSignKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
