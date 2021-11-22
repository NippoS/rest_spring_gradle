package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.FileEntity;

import java.util.List;

public interface FileService {

    FileEntity getById(Long id);

    FileEntity save(FileEntity fileEntity);

    void delete(Long id);

    List<FileEntity> getAll();
}