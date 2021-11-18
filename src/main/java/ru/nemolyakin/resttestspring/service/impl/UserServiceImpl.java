package ru.nemolyakin.resttestspring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.Role;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.repository.RoleRepository;
import ru.nemolyakin.resttestspring.repository.UserRepository;
import ru.nemolyakin.resttestspring.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username){
        log.info("IN UserServiceImpl findByUsername {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User getById(Long id) {
        User user = userRepository.getById(id);

        if (user == null){
            log.warn("IN UserServiceImpl no user found by id: {}", id);
            return null;
        }

        log.info("IN UserServiceImpl getById {}", id);
        return user;
    }

    @Override
    public User save(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registerUser = userRepository.save(user);
        log.info("IN UserServiceImpl save {}", user);

        return  registerUser;
    }

    @Override
    public void delete(Long id) {
        log.info("IN UserServiceImpl delete {}", id);
        User user = userRepository.getById(id);
        user.setStatus(Status.DISABLE);
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        log.info("IN UserServiceImpl getAll");
        return userRepository.findAll();
    }
}