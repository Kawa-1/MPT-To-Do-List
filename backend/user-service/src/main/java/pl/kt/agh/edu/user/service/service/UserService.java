package pl.kt.agh.edu.user.service.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kt.agh.edu.user.service.repository.UserRepository;
import pl.kt.agh.model.dto.InternalUserDTO;
import pl.kt.agh.model.dto.UserCreateDTO;
import pl.kt.agh.model.dto.UserDTO;
import pl.kt.agh.edu.user.service.entity.User;
import pl.kt.agh.model.enums.Role;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        if (userCreateDTO.getRole() == null) {
            user.setRole(Role.CLIENT);
        } else {
            user.setRole(userCreateDTO.getRole());
        }
        User userDB = userRepository.save(user);
        return new UserDTO(
                userDB.getUsername(),
                userDB.getPassword(),
                userDB.getRole()
        );
    }

    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByUsernameAndPassword(login, password);
    }

    public InternalUserDTO findByLogin(String user) {
        User userDB = userRepository.findByUsername(user);
        if (userDB != null) {
            return new InternalUserDTO(
                    userDB.getUsername(),
                    userDB.getPassword(),
                    userDB.getRole(),
                    userDB.getUid()
            );
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
