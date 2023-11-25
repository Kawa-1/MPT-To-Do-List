package pl.kt.agh.edu.user.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kt.agh.edu.entity.User;
import pl.kt.agh.edu.user.service.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user)
    {
        userRepository.save(user);
    }
}
