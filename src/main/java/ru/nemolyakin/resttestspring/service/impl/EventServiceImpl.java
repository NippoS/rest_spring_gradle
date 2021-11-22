package ru.nemolyakin.resttestspring.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.EventEntity;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.repository.EventRepository;
import ru.nemolyakin.resttestspring.service.EventService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventEntity getById(Long id) {
        log.info("IN EventServiceImpl getById {}", id);
        EventEntity event = eventRepository.getById(id);
        if (event == null){
            return null;
        }
        return event;
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        log.info("IN EventServiceImpl save {}", eventEntity);
        return eventRepository.save(eventEntity);
    }

    @Override
    public void delete(Long id) {
        log.info("IN EventServiceImpl delete {}", id);
        EventEntity event = eventRepository.getById(id);
        if (event == null){
           return;
        }
        event.setStatus(Status.DISABLE);
        eventRepository.save(event);
    }

    @Override
    public List<EventEntity> getAll() {
        log.info("IN EventServiceImpl getAll");
        return eventRepository.findAll();
    }
}