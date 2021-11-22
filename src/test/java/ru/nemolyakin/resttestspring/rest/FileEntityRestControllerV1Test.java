package ru.nemolyakin.resttestspring.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import ru.nemolyakin.resttestspring.model.FileEntity;
import ru.nemolyakin.resttestspring.dto.FileDto;
import ru.nemolyakin.resttestspring.service.FileService;
import ru.nemolyakin.resttestspring.service.impl.FileServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FileEntityRestControllerV1Test {

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
//        FileEntity file = new FileEntity();
//        FileEntity file2 = new FileEntity();
//        file.setName("FILENAME");
//        file2.setName("FILENAME2");
//
//        when(fileService.save(file)).thenReturn(getFileEntity());
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


    private FileEntity getFile() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName("FILENAME");
        return fileEntity;
    }

    private FileEntity noFile() {
        return null;
    }

    private List<FileEntity> getAllF() {
        FileEntity fileEntity = new FileEntity();
        FileEntity fileEntity2 = new FileEntity();
        fileEntity.setName("FILENAME");
        fileEntity2.setName("FILENAME2");
        return Stream.of(fileEntity, fileEntity2).collect(Collectors.toList());
    }

    private List<FileEntity> noList() {
        FileEntity fileEntity = new FileEntity();
        FileEntity fileEntity2 = new FileEntity();
        return Stream.of(fileEntity, fileEntity2).collect(Collectors.toList());
    }

    private FileEntity getFileUpdate(){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName("FILENAME2");
        return fileEntity;
    }
}