package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.EventEntity;

import java.util.List;

public interface EventService {

    EventEntity getById(Long id);

    EventEntity save(EventEntity eventEntity);

    void delete(Long id);

    List<EventEntity> getAll();
}