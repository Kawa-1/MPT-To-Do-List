package pl.kt.agh.edu.user.service.controller;

import org.springframework.web.bind.annotation.*;
import pl.kt.agh.edu.common.constant.UserApiPath;
import pl.kt.agh.edu.user.service.service.UserService;
import pl.kt.agh.model.dto.UserAuthDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;
import pl.kt.agh.model.entity.User;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = UserApiPath.INTERNAL_USER_AUTH)
    public UserAuthDTO internalUserFind(@RequestParam("user") String user) {
        User userEntity = userService.findByLogin(user);
        if (userEntity != null) {
            return new UserAuthDTO(
                    userEntity.getLogin(),
                    userEntity.getPassword()
            );
        }
        return null;
    }

    @PostMapping(path = UserApiPath.USER_CREATE)
    public UserDTO createUser(@RequestBody() UserCreateDTO userCreateDTO) {
        UserDTO userDTO = new UserDTO(userCreateDTO.getUsername(), userCreateDTO.getPassword(), null);
        User user = new User();
        user.setLogin(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userService.createUser(user);
        return userDTO;
    }
}
