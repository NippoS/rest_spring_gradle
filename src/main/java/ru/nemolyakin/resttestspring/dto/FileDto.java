package ru.nemolyakin.resttestspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.FileEntity;
import ru.nemolyakin.resttestspring.model.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FileDto {
    private Long id;
    private String name;
    private String location;
    private Status status;

    public FileEntity toFile(){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(id);
        fileEntity.setName(name);
        fileEntity.setLocation(location);
        fileEntity.setStatus(status);
        return fileEntity;
    }

    public static FileDto fromFile(FileEntity fileEntity){
        FileDto fileDto = new FileDto();
        fileDto.setId(fileEntity.getId());
        fileDto.setName(fileEntity.getName());
        fileDto.setLocation(fileEntity.getLocation());
        fileDto.setStatus(fileEntity.getStatus());
        return fileDto;
    }
}
