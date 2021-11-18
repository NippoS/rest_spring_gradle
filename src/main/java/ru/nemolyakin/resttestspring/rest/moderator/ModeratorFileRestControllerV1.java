package ru.nemolyakin.resttestspring.rest.moderator;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nemolyakin.resttestspring.aws.S3Service;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.model.dto.admin.AdminFileDto;
import ru.nemolyakin.resttestspring.service.FileService;

import javax.validation.Valid;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/moderator/files/")
public class ModeratorFileRestControllerV1 {

    @Autowired
    private FileService fileService;
    @Autowired
    private S3Service s3Service;

    @GetMapping("{id}")
    public ResponseEntity<AdminFileDto> getFileById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        File file = this.fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AdminFileDto fileDto = AdminFileDto.fromFile(file);

        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdminFileDto> saveFile(@RequestParam("file") MultipartFile file) {
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
        AdminFileDto fileDto = AdminFileDto.fromFile(newFile);
        return new ResponseEntity<>(fileDto, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminFileDto> deleteFile(@PathVariable("id") Long id) {
        File file = this.fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<AdminFileDto>> getAllFiles() {
        List<File> files = this.fileService.getAll();

        if (files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AdminFileDto> fileDtos = new ArrayList<>();
        files.forEach(file -> fileDtos.add(AdminFileDto.fromFile(file)));
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @SneakyThrows
    private static java.io.File multipartToFile(MultipartFile multipart, String fileName) {
        java.io.File convFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}