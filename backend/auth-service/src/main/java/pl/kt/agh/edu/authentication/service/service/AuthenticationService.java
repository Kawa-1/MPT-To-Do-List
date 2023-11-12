package pl.kt.agh.edu.authentication.service.service;

import pl.kt.agh.edu.authentication.service.entity.UserDetailsImpl;
import pl.kt.agh.edu.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService implements UserDetailsService {

    private final RestTemplate restTemplate;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = restTemplate.getForObject("https://user-service/auth?user=" + username, UserDTO.class);
        return new UserDetailsImpl(userDTO);
    }
}
