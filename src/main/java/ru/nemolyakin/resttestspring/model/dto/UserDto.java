package ru.nemolyakin.resttestspring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<EventDto> eventList;

    public User toUser() {
        Event event = new Event();
        User user = new User();
        List<Event> eventsList = new ArrayList<>();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (eventsList != null) {
            for (EventDto eventsDto : eventList) {
                event.setId(eventsDto.getId());
                event.setDate(eventsDto.getDate());
                eventsList.add(event);
            }
            user.setEvents(eventsList);
        }
        return user;
    }

    public static UserDto fromUser(User user) {
        List<EventDto> eventList = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        if (user.getEvents() != null) {
            for (Event event : user.getEvents()) {
                EventDto eventDto = new EventDto();
                eventDto.setId(event.getId());
                eventDto.setDate(event.getDate());
                eventList.add(eventDto);
            }
            userDto.setEventList(eventList);
        }
        return userDto;
    }
}