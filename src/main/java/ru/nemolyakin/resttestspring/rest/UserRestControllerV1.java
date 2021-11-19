package ru.nemolyakin.resttestspring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.model.dto.UserDto;
import ru.nemolyakin.resttestspring.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {

    private UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.getById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.save(user);
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.delete(user.getId());

        User userNew = new User();
        userNew.setUsername(user.getUsername());
        userNew.setPassword(user.getPassword());
        userNew.setFirstName(user.getFirstName());
        userNew.setLastName(user.getLastName());
        userNew.setRoles(user.getRoles());
        userNew.setEvents(user.getEvents());
        userNew.setStatus(Status.ACTIVE);

        this.userService.save(userNew);
        UserDto userDto = UserDto.fromUser(userNew);
        return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        User user = this.userService.getById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = this.userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(UserDto.fromUser(user)));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}