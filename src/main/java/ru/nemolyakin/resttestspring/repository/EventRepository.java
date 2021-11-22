package ru.nemolyakin.resttestspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.resttestspring.model.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}