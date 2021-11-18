package ru.nemolyakin.resttestspring.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.Status;

import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminFileDto {
    private Long id;
    private String name;
    private URL location;
    private Status status;

    public File toFile(){
        File file = new File();
        file.setId(id);
        file.setName(name);
        file.setLocation(location);
        file.setStatus(status);
        return file;
    }

    public static AdminFileDto fromFile(File file){
        AdminFileDto fileDto = new AdminFileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setLocation(file.getLocation());
        fileDto.setStatus(file.getStatus());
        return fileDto;
    }
}
