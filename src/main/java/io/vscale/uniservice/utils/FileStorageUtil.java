package io.vscale.uniservice.utils;

import io.vscale.uniservice.domain.FileOfService;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 05.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Component
public class FileStorageUtil {

    @Value("${storage.path}")
    private String storagePath;

    @Value("${url.path}")
    private String urlPath;

    public String getStoragePath() {
        return storagePath;
    }

    @SneakyThrows
    public void copyToStorage(MultipartFile file, String storageFileName) {
        Files.copy(file.getInputStream(), Paths.get(storagePath, storageFileName));
    }

    public FileOfService convertFromMultipart(MultipartFile file) {
        // получаем название файла
        String originalFileName = file.getOriginalFilename();
        // вытаскиваем контент-тайп
        String type = file.getContentType();

        // создаем имя файла
        String storageName = createStorageName(originalFileName);
        // получаем urlPath файла по которому он будет доступен внутри системы
        String fileUrl = getUrlOfFile(storageName);
        return FileOfService.builder()
                            .originalName(originalFileName)
                            .encodedName(storageName)
                            .url(fileUrl)
                            .type(type)
                            .build();
    }

    private String getUrlOfFile(String storageFileName) {
        return urlPath + "\\" + storageFileName;
    }

    private String createStorageName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString();
        return newFileName + "." + extension;
    }
}
