package pl.kt.agh.edu.authentication.service.controller;

import org.springframework.web.bind.annotation.*;
import pl.kt.agh.edu.authentication.service.dto.JwtDTO;
import pl.kt.agh.edu.authentication.service.service.AuthenticationService;
import pl.kt.agh.model.dto.InternalUserDTO;
import pl.kt.agh.model.dto.UserAuthDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("validate")
    public void validate(@RequestParam("token") String token) {
        authenticationService.validateToken(token);
    }

    @PostMapping("register")
    public UserDTO register(@RequestBody() UserCreateDTO userCreateDTO) {
        return authenticationService.registerUser(userCreateDTO);
    }

    @PostMapping("login")
    public JwtDTO login(@RequestBody() UserAuthDTO userAuthRequest) throws AuthenticationException {
        return authenticationService.loginUser(userAuthRequest);
    }

}
