package ru.nemolyakin.resttestspring.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.UserEntity;
import ru.nemolyakin.resttestspring.dto.UserDto;
import ru.nemolyakin.resttestspring.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> getUserById(@NonNull @PathVariable("id") Long id) {
        UserEntity user = userService.getById(id);
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> saveUser(@NonNull @RequestBody @Valid UserEntity userEntity) {
        userService.save(userEntity);
        UserDto userDto = UserDto.fromUser(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> updateUser(@NonNull @RequestBody @Valid UserEntity userEntity) {
        UserEntity user = userService.save(userEntity);
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<UserDto> deleteUser(@NonNull @PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userService.getAll();

        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtos = users.stream().map(UserDto::fromUser).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}