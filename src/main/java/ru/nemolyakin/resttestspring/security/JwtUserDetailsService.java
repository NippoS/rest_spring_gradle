package ru.nemolyakin.resttestspring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.security.jwt.JwtUser;
import ru.nemolyakin.resttestspring.security.jwt.JwtUserFactory;
import ru.nemolyakin.resttestspring.service.UserService;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN JwtUserDetailsService user with username {} loaded", username);
        return jwtUser;
    }
}