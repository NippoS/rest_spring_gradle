package ru.nemolyakin.resttestspring.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.UserEntity;
import ru.nemolyakin.resttestspring.security.jwt.JwtUser;
import ru.nemolyakin.resttestspring.security.jwt.JwtUserFactory;
import ru.nemolyakin.resttestspring.service.UserService;

@Slf4j
@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByUsername(username);

        JwtUser jwtUser = JwtUserFactory.create(userEntity);
        log.info("IN JwtUserDetailsService userEntity with username {} loaded", username);
        return jwtUser;
    }
}