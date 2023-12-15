package pl.kt.agh.edu.authentication.service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kt.agh.edu.authentication.service.entity.UserDetailsImpl;
import pl.kt.agh.model.dto.InternalUserDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String AUTH_API = "https://user-service" + "/internal/user/auth";

    private final RestTemplate restTemplate;
    public UserDetailsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InternalUserDTO userDTO = restTemplate.getForObject(AUTH_API + "?user=" + username, InternalUserDTO.class);
        return new UserDetailsImpl(userDTO);
    }
}
