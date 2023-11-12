package pl.kt.agh.edu.user.service.controller;

import pl.kt.agh.edu.dto.UserCreateDTO;
import pl.kt.agh.edu.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    private static final Map<String, UserDTO> users = new HashMap<>();

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
        return userDTO;
    }

}
