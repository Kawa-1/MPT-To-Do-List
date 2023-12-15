package pl.kt.agh.edu.user.service.controller;

import org.springframework.web.bind.annotation.*;
import pl.kt.agh.edu.user.service.service.UserService;
import pl.kt.agh.model.dto.InternalUserDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/internal/user/auth")
    public InternalUserDTO internalUserFind(@RequestParam("user") String user) {
        return userService.findByLogin(user);
    }

    @PostMapping(path = "/user/create")
    public UserDTO createUser(@RequestBody() UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }
}
