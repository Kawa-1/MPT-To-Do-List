package pl.kt.agh.edu.authentication.service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kt.agh.edu.authentication.service.entity.UserDetailsImpl;
import pl.kt.agh.edu.common.constant.UserApiPath;
import pl.kt.agh.model.dto.UserDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String AUTH_API = "https://user-service" + UserApiPath.INTERNAL_USER_AUTH;

    private final RestTemplate restTemplate;
    public UserDetailsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = restTemplate.getForObject(AUTH_API + "?user=" + username, UserDTO.class);
        return new UserDetailsImpl(userDTO);
    }
}
