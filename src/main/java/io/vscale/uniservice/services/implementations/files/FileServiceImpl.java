package io.vscale.uniservice.services.implementations.files;

import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.repositories.FileOfServiceRepository;
import io.vscale.uniservice.services.interfaces.files.FileService;

import java.util.List;

/**
 * 02.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public class FileServiceImpl implements FileService {

    private FileOfServiceRepository filesRepository;

    public FileServiceImpl(FileOfServiceRepository repository){
        this.filesRepository = repository;
    }

    @Override
    public List<FileOfService> findAllByType(String type) {
        return filesRepository.findAllByType(type);
    }
}
