package ru.nemolyakin.resttestspring.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.nemolyakin.resttestspring.model.Role;
import ru.nemolyakin.resttestspring.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getUsername(), user.getFirstName(),
                user.getLastName(), user.getPassword(), mapToGrandedAuthirities(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrandedAuthirities(List<Role> userRoles){
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}