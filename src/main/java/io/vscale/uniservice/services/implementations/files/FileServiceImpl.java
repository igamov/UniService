package io.vscale.uniservice.services.implementations.files;

import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.repositories.data.FileOfServiceRepository;
import io.vscale.uniservice.services.interfaces.files.FileService;
import io.vscale.uniservice.utils.FileStorageUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 02.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Service
public class FileServiceImpl implements FileService {

    private FileOfServiceRepository filesRepository;
    private FileStorageUtil fileStorageUtil;

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    public FileServiceImpl(FileOfServiceRepository repository, FileStorageUtil fileStorageUtil){
        this.filesRepository = repository;
        this.fileStorageUtil = fileStorageUtil;
    }

    @Override
    public List<FileOfService> findAllByType(String type) {
        return this.filesRepository.findAllByType(type);
    }


    @Override
    public void save(FileOfService fileOfService) {
        this.filesRepository.save(fileOfService);
    }

    @Override
    public FileOfService findById(Long id) {
        return this.filesRepository.findOneById(id);
    }

    @Override
    public String save(MultipartFile file) {
        FileOfService fileOfService  = this.fileStorageUtil.convertFromMultipart(file);
        this.filesRepository.save(fileOfService);

        this.fileStorageUtil.copyToStorage(file, fileOfService.getEncodedName());
        return fileOfService.getEncodedName();
    }

    @Override
    public void delete(FileOfService file) {
        this.filesRepository.delete(file);
        File storagedFile = new File(this.storagePath + "\\"+ file.getEncodedName());
        if (storagedFile.exists()){
            storagedFile.delete();
        }
    }

    @SneakyThrows
    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        FileOfService file = this.filesRepository.findOneByEncodedName(fileName);
        response.setContentType(file.getType());
        // получили инпут стрим файла на диске
        InputStream inputStream = new FileInputStream(new File(this.storagePath + "//" + file.getEncodedName()));
        // скопировали файл в ответ
        IOUtils.copy(inputStream, response.getOutputStream());
        // пробрасываем буфер
        response.flushBuffer();
    }
}
