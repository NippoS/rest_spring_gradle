package ru.nemolyakin.resttestspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.resttestspring.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}