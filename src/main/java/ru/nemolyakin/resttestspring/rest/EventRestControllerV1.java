package ru.nemolyakin.resttestspring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.dto.EventDto;
import ru.nemolyakin.resttestspring.service.EventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events/")
public class EventRestControllerV1 {

    private EventService eventService;

    @Autowired
    public EventRestControllerV1(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Event event = this.eventService.getById(id);

        if (event == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EventDto eventDto = EventDto.fromEvent(event);

        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PostMapping("")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> saveEvent(@RequestBody @Valid Event event){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (event == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.eventService.save(event);
        EventDto eventDto = EventDto.fromEvent(event);
        return new ResponseEntity<>(eventDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> updateEvent(@RequestBody @Valid Event event){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (event == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.eventService.delete(event.getId());

        Event eventNew = new Event();
        eventNew.setFile(event.getFile());
        eventNew.setDate(event.getDate());
        eventNew.setUserId(event.getUserId());
        eventNew.setFileId(event.getFileId());
        eventNew.setStatus(Status.ACTIVE);

        this.eventService.save(eventNew);
        EventDto eventDto = EventDto.fromEvent(eventNew);
        return new ResponseEntity<>(eventDto, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<EventDto> deleteEvent(@PathVariable("id") Long id){
        Event event = this.eventService.getById(id);

        if (event == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<List<EventDto>> getAllEvents(){
        List<Event> events = this.eventService.getAll();

        if (events.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EventDto> eventDtos = new ArrayList<>();
        events.forEach(event -> eventDtos.add(EventDto.fromEvent(event)));
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
}