package ru.nemolyakin.resttestspring.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.Event;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminUserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<AdminEventDto> eventList;
    private Status status;

    public User toUser() {
        Event event = new Event();
        User user = new User();
        List<Event> eventsList = new ArrayList<>();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (eventsList != null) {
            for (AdminEventDto eventsDto : eventList) {
                event.setId(eventsDto.getId());
                event.setDate(eventsDto.getDate());
                event.setStatus(eventsDto.getStatus());
                eventsList.add(event);
            }
            user.setEvents(eventsList);
        }
        user.setStatus(status);
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        List<AdminEventDto> eventList = new ArrayList<>();
        AdminUserDto userDto = new AdminUserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        if (user.getEvents() != null) {
            for (Event event : user.getEvents()) {
                AdminEventDto eventDto = new AdminEventDto();
                eventDto.setId(event.getId());
                eventDto.setDate(event.getDate());
                eventDto.setStatus(event.getStatus());
                eventList.add(eventDto);
            }
            userDto.setEventList(eventList);
        }
        userDto.setStatus(user.getStatus());
        return userDto;
    }
}
