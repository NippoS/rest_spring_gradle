package ru.nemolyakin.resttestspring.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.dto.EventDto;
import ru.nemolyakin.resttestspring.service.EventService;

@RestController
@RequestMapping("/api/v1/events/")
public class UserEventRestControllerV1 {

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
}