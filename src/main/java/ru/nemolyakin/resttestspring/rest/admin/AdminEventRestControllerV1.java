package ru.nemolyakin.resttestspring.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.dto.admin.AdminEventDto;
import ru.nemolyakin.resttestspring.service.EventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/events/")
public class AdminEventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping("{id}")
    public ResponseEntity<AdminEventDto> getEventById(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Event event = this.eventService.getById(id);

        if (event == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AdminEventDto eventDto = AdminEventDto.fromEvent(event);

        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AdminEventDto> saveEvent(@RequestBody @Valid Event event){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (event == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.eventService.save(event);
        AdminEventDto eventDto = AdminEventDto.fromEvent(event);
        return new ResponseEntity<>(eventDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<AdminEventDto> updateEvent(@RequestBody @Valid Event event, UriComponentsBuilder builder){
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
        AdminEventDto eventDto = AdminEventDto.fromEvent(eventNew);
        return new ResponseEntity<>(eventDto, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminEventDto> deleteEvent(@PathVariable("id") Long id){
        Event event = this.eventService.getById(id);

        if (event == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<AdminEventDto>> getAllEvents(){
        List<Event> events = this.eventService.getAll();

        if (events.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AdminEventDto> eventDtos = new ArrayList<>();
        events.forEach(event -> eventDtos.add(AdminEventDto.fromEvent(event)));
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
}