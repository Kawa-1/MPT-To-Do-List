package pl.kt.agh.edu.user.service.service;


import org.springframework.stereotype.Service;
import pl.kt.agh.edu.user.service.repository.UserRepository;
import pl.kt.agh.model.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public User findByLogin(String user) {
        return userRepository.findByLogin(user);
    }
}
