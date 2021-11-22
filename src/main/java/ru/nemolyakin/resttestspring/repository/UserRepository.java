package ru.nemolyakin.resttestspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.resttestspring.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String name);
}