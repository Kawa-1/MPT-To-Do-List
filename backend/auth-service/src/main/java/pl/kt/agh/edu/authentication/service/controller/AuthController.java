package pl.kt.agh.edu.authentication.service.controller;

import pl.kt.agh.edu.authentication.service.config.AuthenticationConfigProps;
import pl.kt.agh.edu.authentication.service.dto.JwtDTO;
import pl.kt.agh.edu.common.exception.AuthenticationException;
import pl.kt.agh.edu.common.util.JwtUtil;
import pl.kt.agh.model.dto.UserAuthDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfigProps configProps;

    public AuthController(AuthenticationManager authenticationManager, RestTemplate restTemplate, PasswordEncoder passwordEncoder, AuthenticationConfigProps configProps) {
        this.authenticationManager = authenticationManager;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        this.configProps = configProps;
    }

    @GetMapping("validate")
    public void validate(@RequestParam("token") String token) {
        JwtUtil.validateToken(token, configProps.getSecret());
    }

    @PostMapping("register")
    public UserDTO register(@RequestBody() UserCreateDTO userCreateDTO) {
        //userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return restTemplate.postForObject("http://user-service/user/create", userCreateDTO, UserDTO.class);
    }

    @PostMapping("login")
    public JwtDTO login(@RequestBody() UserAuthDTO userAuthRequest) {
        UserAuthDTO userAuthDTO = restTemplate.postForObject("http://user-service/user/auth", userAuthRequest, UserAuthDTO.class);
        //Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
        if (userAuthDTO != null && userAuthDTO.getPassword().equals(userAuthRequest.getPassword()))
        {
            System.out.println("STOP");
            //if (authenticate.isAuthenticated()) {
                String jwt = JwtUtil.createToken(
                        userAuthRequest.getUsername(),
                        Collections.emptyMap(),
                        configProps.getExpirationInMillis(),
                        configProps.getSecret()
                );
                return new JwtDTO(jwt);
            //}
        }

        // TODO: Throws 403 instead of 401, fix that
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); 
    }

}
