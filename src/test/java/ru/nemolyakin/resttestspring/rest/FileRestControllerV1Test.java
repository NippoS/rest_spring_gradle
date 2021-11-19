package ru.nemolyakin.resttestspring.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.User;
import ru.nemolyakin.resttestspring.model.dto.FileDto;
import ru.nemolyakin.resttestspring.model.dto.UserDto;
import ru.nemolyakin.resttestspring.service.FileService;
import ru.nemolyakin.resttestspring.service.UserService;
import ru.nemolyakin.resttestspring.service.impl.FileServiceImpl;
import ru.nemolyakin.resttestspring.service.impl.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FileRestControllerV1Test {

    @Mock
    private FileService fileService = mock(FileServiceImpl.class);

    @InjectMocks
    private FileRestControllerV1 underTest = new FileRestControllerV1(fileService);

    @Test
    void getById_Success() {
        when(fileService.getById(any())).thenReturn(getFile());
        ResponseEntity<FileDto> fileDto = underTest.getFileById(2L);
        assertEquals(200, fileDto.getStatusCodeValue());
        assertEquals(true, fileDto.toString().contains("FILENAME"));
    }

    @Test
    void getById_Failed() {
        when(fileService.getById(any())).thenReturn(noFile());
        ResponseEntity<FileDto> fileDto = underTest.getFileById(2L);
        assertEquals(404, fileDto.getStatusCodeValue());
    }

    @Test
    public void testGetAll_Success() {
        when(fileService.getAll()).thenReturn(getAllF());
        ResponseEntity<List<FileDto>> files = underTest.getAllFiles();
        assertEquals(200, files.getStatusCodeValue());
        assertEquals(true, files.toString().contains("FILENAME2"));
    }

    @Test
    public void testGetAll_Failed() {
        when(fileService.getAll()).thenReturn(noList());
        ResponseEntity<List<FileDto>> files = underTest.getAllFiles();
        assertEquals(200, files.getStatusCodeValue());
        assertEquals(false, files.toString().contains("Maxim"));
        assertEquals(false, files.toString().contains("Ivan"));
    }

//    @Test
//    public void testSaveAndUpdate_Success(){
//        File file = new File();
//        File file2 = new File();
//        file.setName("FILENAME");
//        file2.setName("FILENAME2");
//
//        when(fileService.save(file)).thenReturn(getFile());
//        ResponseEntity<FileDto> fileDto = underTest.saveFile((MultipartFile) file);
//        assertEquals(201, fileDto.getStatusCodeValue());
//        assertEquals(true, fileDto.toString().contains("FILENAME"));
//
//        when(fileService.save(any())).thenReturn(getFileUpdate());
//        fileDto = underTest.updateFile("2",(MultipartFile) file2);
//        assertEquals(200, fileDto.getStatusCodeValue());
//        assertEquals(true, fileDto.toString().contains("FILENAME2"));
//    }
//
//    @Test
//    public void testSaveAndUpdate_Fail(){
//        when(fileService.save(any())).thenReturn(noFile());
//        ResponseEntity<FileDto> fileDto = underTest.saveFile(null);
//        assertEquals(400, fileDto.getStatusCodeValue());
//        assertEquals(false, fileDto.toString().contains("Maxim"));
//
//        when(fileService.save(any())).thenReturn(noFile());
//        fileDto = underTest.updateFile("2", null);
//        assertEquals(400, fileDto.getStatusCodeValue());
//        assertEquals(false, fileDto.toString().contains("Ivan"));
//    }

    @Test
    public void testDelete_Success(){
        when(fileService.getById(any())).thenReturn(getFile());
        doNothing().when(fileService).delete(any());
        underTest.deleteFile(1L);
        verify(fileService, times(1)).delete(1L);
    }

    @Test
    public void testDelete_Fail(){
        when(fileService.getById(any())).thenReturn(getFile());
        doNothing().when(fileService).delete(any());
        underTest.deleteFile(1L);
        verify(fileService, times(1)).delete(1L);
    }


    private File getFile() {
        File file = new File();
        file.setName("FILENAME");
        return file;
    }

    private File noFile() {
        return null;
    }

    private List<File> getAllF() {
        File file = new File();
        File file2 = new File();
        file.setName("FILENAME");
        file2.setName("FILENAME2");
        return Stream.of(file, file2).collect(Collectors.toList());
    }

    private List<File> noList() {
        File file = new File();
        File file2 = new File();
        return Stream.of(file, file2).collect(Collectors.toList());
    }

    private File getFileUpdate(){
        File file = new File();
        file.setName("FILENAME2");
        return file;
    }
}