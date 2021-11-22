package ru.nemolyakin.resttestspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.EventEntity;
import ru.nemolyakin.resttestspring.model.FileEntity;
import ru.nemolyakin.resttestspring.model.Status;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EventDto {
    private Long id;
    private Date date;
    private Status status;
    private FileDto file;

    public EventEntity toEvent() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setDate(date);
        if (file != null) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(file.getId());
            fileEntity.setName(file.getName());
            fileEntity.setLocation(file.getLocation());
            fileEntity.setStatus(file.getStatus());
            eventEntity.setFile(fileEntity);
        }
        eventEntity.setStatus(status);
        return eventEntity;
    }

    public static EventDto fromEvent(EventEntity eventEntity) {
        EventDto eventDto = new EventDto();
        eventDto.setId(eventEntity.getId());
        eventDto.setDate(eventEntity.getDate());
        if (eventEntity.getFile() != null) {
            FileDto fileDto = new FileDto();
            fileDto.setId(eventEntity.getFile().getId());
            fileDto.setName(eventEntity.getFile().getName());
            fileDto.setLocation(eventEntity.getFile().getLocation());
            fileDto.setStatus(eventEntity.getFile().getStatus());
            eventDto.setFile(fileDto);
        }
        eventDto.setStatus(eventEntity.getStatus());
        return eventDto;
    }
}