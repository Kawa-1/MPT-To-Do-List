package pl.kt.agh.edu.user.service.controller;

import org.springframework.web.bind.annotation.*;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;
import pl.kt.agh.model.entity.User;
import pl.kt.agh.edu.user.service.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private final UserService userService;
    private static final Map<String, UserDTO> users = new HashMap<>();

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "user/test")
    public String test() {
        return "siema";
    }

    @GetMapping(path = "auth")
    public UserDTO internalUserFind(@RequestParam("user") String user) {
        return users.get(user);
    }

    @PostMapping("user/create")
    public UserDTO createUser(@RequestBody() UserCreateDTO userCreateDTO) {
        UserDTO userDTO = new UserDTO(userCreateDTO.getUsername(), userCreateDTO.getPassword(), null);
        users.put(userCreateDTO.getUsername(), userDTO);
        User user = new User();
        user.setLogin(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userService.addUser(user);
        return userDTO;
    }
}
