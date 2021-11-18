package ru.nemolyakin.resttestspring.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.dto.FileDto;
import ru.nemolyakin.resttestspring.service.FileService;

@RestController
@RequestMapping("/api/v1/files/")
public class UserFileRestControllerV1 {

    @Autowired
    private FileService fileService;

    @GetMapping("{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        File file = this.fileService.getById(id);

        if (file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileDto fileDto = FileDto.fromFile(file);

        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }
}