package io.vscale.uniservice.services;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.repositories.FileOfServiceRepository;
import io.vscale.uniservice.services.interfaces.files.FileService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * 05.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class FileServiceIntegrationTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileOfServiceRepository fileRepository;

    @Value("${storage.path}")
    private String storagePath;

    private String testStoragePath;

    @Before
    @SneakyThrows // создаем файл для тестов
    public void createTxtFile(){
        //для запуска на компе Андрея - D:\Projects\Java\Курс по выбору(2017 - 2018)\2 семестр\UniServiceTestFolder
        this.testStoragePath = "C:\\test\\test.txt";
        File file = new File(this.testStoragePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        PrintWriter writer = new PrintWriter(this.testStoragePath, "UTF-8");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();
    }

    @SneakyThrows
    @Test // Сохранаяем файл и проверяем что он сохранился и удаляем его проверяем удалился ли он
    @Transactional
    public void deleteFile(){
        Path path = Paths.get(this.testStoragePath);
        String name = "file.txt";
        String originalFileName = "text.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);
        MultipartFile file = new MockMultipartFile(name, originalFileName, contentType, content);
        String encodedName = this.fileService.save(file);
        assertTrue(new File(this.storagePath + "\\" + encodedName).exists());
        this.fileService.delete(this.fileRepository.findOneByEncodedName(encodedName));
        assertFalse(new File(this.storagePath + "\\" + encodedName).exists());
    }

}
