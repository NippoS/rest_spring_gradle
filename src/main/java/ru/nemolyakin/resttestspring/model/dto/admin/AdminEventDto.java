package ru.nemolyakin.resttestspring.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.Status;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminEventDto {
    private Long id;
    private Date date;
    private File file;
    private Status status;

    public Event toEvent() {
        Event event = new Event();
        event.setId(id);
        if (file != null) {
            event.setDate(date);
        }
        event.setStatus(status);
        return event;
    }

    public static AdminEventDto fromEvent(Event event) {
        AdminEventDto eventDto = new AdminEventDto();
        eventDto.setId(event.getId());
        eventDto.setDate(event.getDate());
        if (event.getFile() != null) {
            eventDto.setFile(event.getFile());
        }
        eventDto.setStatus(event.getStatus());
        return eventDto;
    }
}