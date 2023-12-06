package pl.kt.agh.edu.authentication.service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kt.agh.edu.authentication.service.config.AuthenticationConfigProps;
import pl.kt.agh.edu.authentication.service.dto.JwtDTO;
import pl.kt.agh.edu.common.constant.UserApiPath;
import pl.kt.agh.edu.common.exception.AuthenticationException;
import pl.kt.agh.edu.common.util.JwtUtil;
import pl.kt.agh.model.dto.UserAuthDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;

import java.util.Collections;

@Service
public class AuthenticationService {
    private static final String CREATE_API = "https://user-service" + UserApiPath.USER_CREATE;

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfigProps configProps;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(RestTemplate restTemplate, PasswordEncoder passwordEncoder, AuthenticationConfigProps configProps, AuthenticationManager authenticationManager) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        this.configProps = configProps;
        this.authenticationManager = authenticationManager;
    }

    public void validateToken(String token) {
        JwtUtil.validateToken(token, configProps.getSecret());
    }

    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return restTemplate.postForObject(CREATE_API, userCreateDTO, UserDTO.class);
    }

    public JwtDTO loginUser(UserAuthDTO userAuthRequest) {
        String username = userAuthRequest.getUsername();
        String password = userAuthRequest.getPassword();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authenticate.isAuthenticated()) {
            String jwt = JwtUtil.createToken(
                    userAuthRequest.getUsername(),
                    Collections.emptyMap(),
                    configProps.getExpirationInMillis(),
                    configProps.getSecret()
            );
            return new JwtDTO(jwt);
        }
        throw new AuthenticationException("Cannot authenticate user");
    }
}
