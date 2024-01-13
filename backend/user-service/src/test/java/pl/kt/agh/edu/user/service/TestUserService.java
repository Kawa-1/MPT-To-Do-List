package pl.kt.agh.edu.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import pl.kt.agh.edu.user.service.entity.User;
import pl.kt.agh.edu.user.service.repository.UserRepository;
import pl.kt.agh.edu.user.service.service.UserService;
import pl.kt.agh.model.dto.InternalUserDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;
import pl.kt.agh.model.enums.Role;


@ExtendWith(MockitoExtension.class)
public class TestUserService {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindByLogin(){
        String username = "test";

        User user = new User();
        user.setUsername(username);

        InternalUserDTO internalUserDTO = new InternalUserDTO();
        internalUserDTO.setUsername(username);

        
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        InternalUserDTO ret = userService.findByLogin(username);
        Assert.isTrue(ret.equals(internalUserDTO), "The return InternalUserDTO is not equal to expected one.");
    }

    @Test
    void testFindByLoginAndPassword(){
        String username = "test";
        String password = "test_password";

        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(expectedUser);

        User ret = userService.findByLoginAndPassword(username, password);
        Assert.isTrue(expectedUser.equals(ret), "Returned user is not equal to the expected one.");
    }

    @Test
    void testCreateUser(){
        String username = "test";
        String password = "test_password";

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername(username);
        userCreateDTO.setPassword(password);
        userCreateDTO.setRole(Role.MECHANIC);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.MECHANIC);

        UserDTO expectedUserDto = new UserDTO();
        expectedUserDto.setUsername(username);
        expectedUserDto.setPassword(password);
        expectedUserDto.setRole(Role.MECHANIC);

        when(userRepository.save(any())).thenReturn(user);

        UserDTO ret = userService.createUser(userCreateDTO);

        Assert.isTrue(expectedUserDto.equals(ret), "Returned userDTO is not equal to the expected one.");
    }
}
