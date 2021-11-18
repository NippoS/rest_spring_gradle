package ru.nemolyakin.resttestspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.resttestspring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}