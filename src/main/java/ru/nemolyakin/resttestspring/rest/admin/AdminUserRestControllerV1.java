package ru.nemolyakin.resttestspring.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.model.dto.admin.AdminUserDto;
import ru.nemolyakin.resttestspring.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/users/")
public class AdminUserRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.getById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AdminUserDto userDto = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AdminUserDto> saveUser(@RequestBody @Valid User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.save(user);
        AdminUserDto userDto = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<AdminUserDto> updateUser(@RequestBody @Valid User user, UriComponentsBuilder builder) {
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
        AdminUserDto userDto = AdminUserDto.fromUser(userNew);
        return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminUserDto> deleteUser(@PathVariable("id") Long id) {
        User user = this.userService.getById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<User> users = this.userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AdminUserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(AdminUserDto.fromUser(user)));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}