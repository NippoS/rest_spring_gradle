package ru.nemolyakin.resttestspring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.repository.EventRepository;
import ru.nemolyakin.resttestspring.service.EventService;

import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event getById(Long id) {
        log.info("IN EventServiceImpl getById {}", id);
        return eventRepository.getById(id);
    }

    @Override
    public void save(Event event) {
        log.info("IN EventServiceImpl save {}", event);
        eventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        log.info("IN EventServiceImpl delete {}", id);
        Event event = eventRepository.getById(id);
        event.setStatus(Status.DISABLE);
        eventRepository.save(event);
    }

    @Override
    public List<Event> getAll() {
        log.info("IN EventServiceImpl getAll");
        return eventRepository.findAll();
    }
}