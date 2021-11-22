package ru.nemolyakin.resttestspring.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import ru.nemolyakin.resttestspring.model.UserEntity;
import ru.nemolyakin.resttestspring.dto.UserDto;
import ru.nemolyakin.resttestspring.service.UserService;
import ru.nemolyakin.resttestspring.service.impl.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserRestControllerV1Test {

    @Mock
    private UserService userService = mock(UserServiceImpl.class);

    @InjectMocks
    private UserRestControllerV1 underTest = new UserRestControllerV1(userService);

    @Test
    void getById_Success() {
        when(userService.getById(any())).thenReturn(getUser());
        ResponseEntity<UserDto> userDto = underTest.getUserById(2L);
        assertEquals(200, userDto.getStatusCodeValue());
        assertEquals(true, userDto.toString().contains("Maxim"));
    }

    @Test
    void getById_Failed() {
        when(userService.getById(any())).thenReturn(noUser());
        ResponseEntity<UserDto> userDto = underTest.getUserById(2L);
        assertEquals(404, userDto.getStatusCodeValue());
    }

    @Test
    public void testGetAll_Success() {
        when(userService.getAll()).thenReturn(getAllU());
        ResponseEntity<List<UserDto>> users = underTest.getAllUsers();
        assertEquals(200, users.getStatusCodeValue());
        assertEquals(true, users.toString().contains("Ivan"));
    }

    @Test
    public void testGetAll_Failed() {
        when(userService.getAll()).thenReturn(noList());
        ResponseEntity<List<UserDto>> users = underTest.getAllUsers();
        assertEquals(200, users.getStatusCodeValue());
        assertEquals(false, users.toString().contains("Maxim"));
        assertEquals(false, users.toString().contains("Ivan"));
    }

    @Test
    public void testSaveAndUpdate_Success(){
        UserEntity userEntity = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        userEntity.setFirstName("Maxim");
        userEntity2.setFirstName("Ivan");

        when(userService.save(any())).thenReturn(getUser());
        ResponseEntity<UserDto> userDto = underTest.saveUser(userEntity);
        assertEquals(201, userDto.getStatusCodeValue());
        assertEquals(true, userDto.toString().contains("Maxim"));

        when(userService.save(any())).thenReturn(getUserUpdate());
        userDto = underTest.updateUser(userEntity2);
        assertEquals(200, userDto.getStatusCodeValue());
        assertEquals(true, userDto.toString().contains("Ivan"));
    }

    @Test
    public void testSaveAndUpdate_Fail(){
        when(userService.save(any())).thenReturn(noUser());
        ResponseEntity<UserDto> userDto = underTest.saveUser(null);
        assertEquals(400, userDto.getStatusCodeValue());
        assertEquals(false, userDto.toString().contains("Maxim"));

        when(userService.save(any())).thenReturn(noUser());
        userDto = underTest.updateUser(null);
        assertEquals(400, userDto.getStatusCodeValue());
        assertEquals(false, userDto.toString().contains("Ivan"));
    }

    @Test
    public void testDelete_Success(){
        when(userService.getById(any())).thenReturn(getUser());
        doNothing().when(userService).delete(any());
        underTest.deleteUser(1L);
        verify(userService, times(1)).delete(1L);
    }

    @Test
    public void testDelete_Fail(){
        when(userService.getById(any())).thenReturn(getUser());
        doNothing().when(userService).delete(any());
        underTest.deleteUser(1L);
        verify(userService, times(1)).delete(1L);
    }


    private UserEntity getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Maxim");
        return userEntity;
    }

    private UserEntity noUser() {
        return null;
    }

    private List<UserEntity> getAllU() {
        UserEntity userEntity = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        userEntity.setFirstName("Maxim");
        userEntity2.setFirstName("Ivan");
        return Stream.of(userEntity, userEntity2).collect(Collectors.toList());
    }

    private List<UserEntity> noList() {
        UserEntity userEntity = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        return Stream.of(userEntity, userEntity2).collect(Collectors.toList());
    }

    private UserEntity getUserUpdate(){
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Ivan");
        return userEntity;
    }
}