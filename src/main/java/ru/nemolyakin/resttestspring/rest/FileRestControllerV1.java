package ru.nemolyakin.resttestspring.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nemolyakin.resttestspring.aws.S3Service;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.dto.FileDto;
import ru.nemolyakin.resttestspring.service.FileService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/files/")
public class FileRestControllerV1 {
    private FileService fileService;
    @Autowired
    private S3Service s3Service;

    @Autowired
    public FileRestControllerV1(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("{id}")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> getFileById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        File file = this.fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileDto fileDto = FileDto.fromFile(file);

        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> saveFile(@RequestParam("file") MultipartFile file) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        java.io.File fileF = multipartToFile(file, file.getOriginalFilename());
        URL url = s3Service.uploadFile(fileF);

        File newFile = new File();
        newFile.setName(file.getOriginalFilename().replaceAll("\\..*", ""));
        newFile.setLocation(url);
        newFile.setStatus(Status.ACTIVE);

        this.fileService.save(newFile);
        FileDto fileDto = FileDto.fromFile(newFile);
        return new ResponseEntity<>(fileDto, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> updateFile(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String fileName = file.getOriginalFilename();
        this.fileService.delete(Long.parseLong(id));

        java.io.File fileF = multipartToFile(file, fileName);
        URL url = s3Service.uploadFile(fileF);

        File newFile = new File();
        newFile.setName(fileName.replaceAll("\\..*", ""));
        newFile.setLocation(url);
        newFile.setStatus(Status.ACTIVE);

        this.fileService.save(newFile);
        FileDto fileDto = FileDto.fromFile(newFile);
        return new ResponseEntity<>(fileDto, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> deleteFile(@PathVariable("id") Long id) {
        File file = this.fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<List<FileDto>> getAllFiles() {
        List<File> files = this.fileService.getAll();

        if (files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FileDto> fileDtos = new ArrayList<>();
        files.forEach(file -> fileDtos.add(FileDto.fromFile(file)));
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @SneakyThrows
    private static java.io.File multipartToFile(MultipartFile multipart, String fileName) {
        java.io.File convFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}