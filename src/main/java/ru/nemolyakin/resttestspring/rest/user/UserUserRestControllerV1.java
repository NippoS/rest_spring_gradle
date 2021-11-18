package ru.nemolyakin.resttestspring.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.dto.UserDto;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.service.UserService;

@RestController
@RequestMapping("/api/v1/users/")
public class UserUserRestControllerV1 {

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
}