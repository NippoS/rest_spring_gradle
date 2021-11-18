package ru.nemolyakin.resttestspring.rest.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.dto.UserDto;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/moderator/users/")
public class ModeratorUserRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.getById(id);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid User user){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.save(user);
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id){
        User user = this.userService.getById(id);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> users = this.userService.getAll();

        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(UserDto.fromUser(user)));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}