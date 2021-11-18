package ru.nemolyakin.resttestspring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.File;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EventDto {
    private Long id;
    private Date date;
    private File file;

    public Event toEvent() {
        Event event = new Event();
        event.setId(id);
        if (file != null) {
            event.setDate(date);
        }
        return event;
    }

    public static EventDto fromEvent(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setDate(event.getDate());
        if (event.getFile() != null) {
            eventDto.setFile(event.getFile());
        }
        return eventDto;
    }
}