package ru.nemolyakin.resttestspring.rest.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.dto.EventDto;
import ru.nemolyakin.resttestspring.service.EventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/moderator/events/")
public class ModeratorEventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping("{id}")
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
    public ResponseEntity<EventDto> saveEvent(@RequestBody @Valid Event event){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (event == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.eventService.save(event);
        EventDto eventDto = EventDto.fromEvent(event);
        return new ResponseEntity<>(eventDto, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<EventDto> deleteEvent(@PathVariable("id") Long id){
        Event event = this.eventService.getById(id);

        if (event == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
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