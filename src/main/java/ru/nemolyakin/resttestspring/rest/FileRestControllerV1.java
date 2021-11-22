package ru.nemolyakin.resttestspring.rest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nemolyakin.resttestspring.aws.S3Service;
import ru.nemolyakin.resttestspring.model.FileEntity;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.dto.FileDto;
import ru.nemolyakin.resttestspring.service.FileService;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/files")
public class FileRestControllerV1 {

    private final FileService fileService;
    private final S3Service s3Service;

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> getFileById(@NonNull @PathVariable("id") Long id) {
        FileEntity fileEntity = fileService.getById(id);
        FileDto fileDto = FileDto.fromFile(fileEntity);
        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> saveFile(@NonNull @RequestParam("file") MultipartFile file) {
        File fileF = multipartToFile(file, file.getOriginalFilename());
        String url = s3Service.uploadFile(fileF);

        FileEntity newFileEntity = new FileEntity();
        newFileEntity.setName(file.getOriginalFilename().replaceAll("\\..*", ""));
        newFileEntity.setLocation(url);
        newFileEntity.setStatus(Status.ACTIVE);

        fileService.save(newFileEntity);
        FileDto fileDto = FileDto.fromFile(newFileEntity);
        return new ResponseEntity<>(fileDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> updateFile(@NonNull @RequestParam("id") String id, @NonNull @RequestParam("file") MultipartFile file) {
        fileService.delete(Long.parseLong(id));

        String fileName = file.getOriginalFilename();
        File fileF = multipartToFile(file, fileName);
        String url = s3Service.uploadFile(fileF);

        FileEntity newFileEntity = new FileEntity();
        newFileEntity.setName(fileName.replaceAll("\\..*", ""));
        newFileEntity.setLocation(url);
        newFileEntity.setStatus(Status.ACTIVE);

        fileService.save(newFileEntity);
        FileDto fileDto = FileDto.fromFile(newFileEntity);
        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<FileDto> deleteFile(@NonNull @PathVariable("id") Long id) {
        this.fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<List<FileDto>> getAllFiles() {
        List<FileEntity> files = fileService.getAll();

        if (CollectionUtils.isEmpty(files)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FileDto> fileDtos = files.stream().map(FileDto::fromFile).collect(Collectors.toList());
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @SneakyThrows
    private static File multipartToFile(MultipartFile multipart, String fileName) {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}