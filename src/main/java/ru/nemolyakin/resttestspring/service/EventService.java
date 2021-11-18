package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.Event;

import java.util.List;

public interface EventService {

    Event getById(Long id);

    void save(Event event);

    void delete(Long id);

    List<Event> getAll();
}