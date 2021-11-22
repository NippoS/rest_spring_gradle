package ru.nemolyakin.resttestspring.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.nemolyakin.resttestspring.model.Role;
import ru.nemolyakin.resttestspring.model.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserEntity userEntity){
        return new JwtUser(userEntity.getId(), userEntity.getUsername(), userEntity.getFirstName(),
                userEntity.getLastName(), userEntity.getPassword(), mapToGrandedAuthirities(new ArrayList<>(userEntity.getRoles())), userEntity.getStatus());
    }

    private static List<GrantedAuthority> mapToGrandedAuthirities(List<Role> userRoles){
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}