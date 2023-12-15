package pl.kt.agh.edu.authentication.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kt.agh.edu.authentication.service.dto.JwtDTO;
import pl.kt.agh.edu.authentication.service.entity.UserDetailsImpl;
import pl.kt.agh.model.dto.UserAuthDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final String CREATE_API = "https://user-service" + "/user/create";

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtResolverExtService jwtResolverExtService;

    public boolean validateToken(String token) {
        return jwtResolverExtService.isTokenValid(token);
    }

    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return restTemplate.postForObject(CREATE_API, userCreateDTO, UserDTO.class);
    }

    public JwtDTO loginUser(UserAuthDTO userAuthRequest) throws AuthenticationException {
        String username = userAuthRequest.getUsername();
        String password = userAuthRequest.getPassword();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authenticate.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            Map<String, Object> claims = Map.of(
                    "role", userDetails.getUserDTO().getRole().name(),
                    "uid", userDetails.getUserDTO().getId()
            );
            String jwt = jwtResolverExtService.generateToken(claims, userDetails);
            return new JwtDTO(jwt, userDetails.getUserDTO().getRole());
        }
        throw new AuthenticationException("Cannot authenticate user");
    }
}
