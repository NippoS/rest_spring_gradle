package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findByUsername(String username);

    UserEntity getById(Long id);

    UserEntity save(UserEntity userEntity);

    void delete(Long id);

    List<UserEntity> getAll();
}