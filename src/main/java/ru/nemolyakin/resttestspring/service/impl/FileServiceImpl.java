package ru.nemolyakin.resttestspring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nemolyakin.resttestspring.model.File;
import ru.nemolyakin.resttestspring.model.Status;
import ru.nemolyakin.resttestspring.repository.FileRepository;
import ru.nemolyakin.resttestspring.service.FileService;

import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public File getById(Long id) {
        log.info("IN FileServiceImpl getById {}", id);
        return fileRepository.getById(id);
    }

    @Override
    public void save(File file) {
        log.info("IN FileServiceImpl save {}", file);
        fileRepository.save(file);
    }

    @Override
    public void delete(Long id) {
        log.info("IN FileServiceImpl delete {}", id);
        File file = fileRepository.getById(id);
        file.setStatus(Status.DISABLE);
        fileRepository.save(file);
    }

    @Override
    public List<File> getAll() {
        log.info("IN FileServiceImpl getAll");
        return fileRepository.findAll();
    }
}