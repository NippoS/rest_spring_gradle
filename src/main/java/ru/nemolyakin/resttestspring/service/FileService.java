package ru.nemolyakin.resttestspring.service;

import ru.nemolyakin.resttestspring.model.File;

import java.util.List;

public interface FileService {

    File getById(Long id);

    void save(File file);

    void delete(Long id);

    List<File> getAll();
}