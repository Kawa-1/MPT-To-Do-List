package pl.kt.agh.edu.common.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.kt.agh.edu.commons.jwt.JwtResolver;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilterBase extends OncePerRequestFilter {
    private final JwtResolver jwtResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        try {
            if (jwtResolver.isTokenValid(jwt)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                String userName = jwtResolver.extractUserName(jwt);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userName, jwt, null);
                authToken.setDetails(jwtResolver.extractAllClaimsAsMap(jwt));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        } catch (JwtException ignored) {
        }
        filterChain.doFilter(request, response);
    }
}
