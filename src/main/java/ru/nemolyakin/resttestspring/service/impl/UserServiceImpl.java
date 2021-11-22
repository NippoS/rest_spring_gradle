package ru.nemolyakin.resttestspring.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.Role;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.UserEntity;
import ru.nemolyakin.resttestspring.repository.RoleRepository;
import ru.nemolyakin.resttestspring.repository.UserRepository;
import ru.nemolyakin.resttestspring.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserEntity findByUsername(String username){
        log.info("IN UserServiceImpl findByUsername {}", username);
        UserEntity user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("UserEntity with username: " + username + " not found");
        }
        return user;
    }

    @Override
    public UserEntity getById(Long id) {
        UserEntity user = userRepository.getById(id);

        if (user == null){
            log.warn("IN UserServiceImpl no userEntity found by id: {}", id);
            return null;
        }
        log.info("IN UserServiceImpl getById {}", id);
        return user;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(userRoles);

        UserEntity registerUserEntity = userRepository.save(userEntity);
        log.info("IN UserServiceImpl save {}", userEntity);

        return registerUserEntity;
    }

    @Override
    public void delete(Long id) {
        log.info("IN UserServiceImpl delete {}", id);
        UserEntity user = userRepository.getById(id);
        if (user == null){
            return;
        }
        user.setStatus(Status.DISABLE);
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAll() {
        log.info("IN UserServiceImpl getAll");
        return userRepository.findAll();
    }
}