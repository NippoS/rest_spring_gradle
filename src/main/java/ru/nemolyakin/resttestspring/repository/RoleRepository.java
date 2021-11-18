package ru.nemolyakin.resttestspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.resttestspring.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}