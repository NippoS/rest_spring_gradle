package ru.nemolyakin.resttestspring.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.dto.EventDto;
import ru.nemolyakin.resttestspring.model.EventEntity;
import ru.nemolyakin.resttestspring.service.EventService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    private EventService eventService;

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<EventDto> getEventById(@NonNull @PathVariable("id") Long id) {
        EventEntity eventEntity = eventService.getById(id);
        EventDto eventDto = EventDto.fromEvent(eventEntity);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> saveEvent(@NonNull @RequestBody @Valid EventEntity eventEntity) {
        eventService.save(eventEntity);
        EventDto eventDto = EventDto.fromEvent(eventEntity);
        return new ResponseEntity<>(eventDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> updateEvent(@NonNull @RequestBody @Valid EventEntity eventEntity) {
        EventEntity updatedEventEntity = eventService.save(eventEntity);
        EventDto eventDto = EventDto.fromEvent(updatedEventEntity);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> deleteEvent(@NonNull @PathVariable("id") Long id) {
        eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventEntity> events = eventService.getAll();

        if (CollectionUtils.isEmpty(events)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EventDto> eventDtos = events.stream().map(EventDto::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
}