package pl.kt.agh.edu.car.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kt.agh.edu.common.filter.JwtFilterBase;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.edu.commons.jwt.JwtResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    JwtResolver jwtResolver(@Value("${auth.jwt.secret}") String jwtSecret) {
        return new JwtResolver(jwtSecret);
    }

    @Bean
    SecurityContextConverter securityContextConverter() {
        return new SecurityContextConverter();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain userApiSecurityFilterChain(HttpSecurity http, JwtResolver jwtResolver) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .securityMatchers(matcher -> matcher.requestMatchers("/car/**"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .addFilterBefore(new JwtFilterBase(jwtResolver), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
