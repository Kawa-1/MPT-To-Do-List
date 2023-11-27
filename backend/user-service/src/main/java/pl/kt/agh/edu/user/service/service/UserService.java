package pl.kt.agh.edu.user.service.service;

import org.springframework.stereotype.Service;
import pl.kt.agh.model.entity.User;
import pl.kt.agh.edu.user.service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }
}
