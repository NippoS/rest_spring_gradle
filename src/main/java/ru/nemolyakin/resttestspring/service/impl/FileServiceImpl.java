package ru.nemolyakin.resttestspring.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.FileEntity;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.repository.FileRepository;
import ru.nemolyakin.resttestspring.service.FileService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public FileEntity getById(Long id) {
        log.info("IN FileServiceImpl getById {}", id);
        FileEntity file = fileRepository.getById(id);
        if (file == null){
            return null;
        }
        return file;
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        log.info("IN FileServiceImpl save {}", fileEntity);
        return fileRepository.save(fileEntity);
    }

    @Override
    public void delete(Long id) {
        log.info("IN FileServiceImpl delete {}", id);
        FileEntity file = fileRepository.getById(id);
        if (file == null){
            return;
        }
        file.setStatus(Status.DISABLE);
        fileRepository.save(file);
    }

    @Override
    public List<FileEntity> getAll() {
        log.info("IN FileServiceImpl getAll");
        return fileRepository.findAll();
    }
}