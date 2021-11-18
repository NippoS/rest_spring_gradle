package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User getById(Long id);

    User save(User user);

    void delete(Long id);

    List<User> getAll();
}