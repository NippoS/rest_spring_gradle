package ru.nemolyakin.resttestspring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.File;

import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FileDto {
    private Long id;
    private String name;
    private URL location;

    public File toFile(){
        File file = new File();
        file.setId(id);
        file.setName(name);
        file.setLocation(location);
        return file;
    }

    public static FileDto fromFile(File file){
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setLocation(file.getLocation());
        return fileDto;
    }
}