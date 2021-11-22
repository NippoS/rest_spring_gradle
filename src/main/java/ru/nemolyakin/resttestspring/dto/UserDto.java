package ru.nemolyakin.resttestspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.EventEntity;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Status status;
    private List<EventDto> eventList;

    public UserEntity toUser() {
        EventEntity eventEntity = new EventEntity();
        UserEntity userEntity = new UserEntity();
        List<EventEntity> eventsList = new ArrayList<>();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setStatus(status);
        if (eventsList != null) {
            for (EventDto eventsDto : eventList) {
                eventEntity.setId(eventsDto.getId());
                eventEntity.setDate(eventsDto.getDate());
                eventEntity.setStatus(eventsDto.getStatus());
                eventsList.add(eventEntity);
            }
            userEntity.setEventEntities(eventsList);
        }
        return userEntity;
    }

    public static UserDto fromUser(UserEntity userEntity) {
        List<EventDto> eventList = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setStatus(userEntity.getStatus());
        if (userEntity.getEventEntities() != null) {
            for (EventEntity eventEntity : userEntity.getEventEntities()) {
                EventDto eventDto = new EventDto();
                eventDto.setId(eventEntity.getId());
                eventDto.setDate(eventEntity.getDate());
                eventDto.setStatus(eventEntity.getStatus());
                eventList.add(eventDto);
            }
            userDto.setEventList(eventList);
        }
        return userDto;
    }
}
