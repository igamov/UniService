package io.vscale.uniservice.services.implementations.files;

import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.FileOfServiceRepository;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import io.vscale.uniservice.services.interfaces.auth.AuthenticationService;
import io.vscale.uniservice.services.interfaces.files.FileService;
import io.vscale.uniservice.utils.FileStorageUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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

    private final FileOfServiceRepository filesRepository;
    private final FileStorageUtil fileStorageUtil;
    private final AuthenticationService authenticationService;
    private final ProfileRepository profileRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    public FileServiceImpl(FileOfServiceRepository repository, FileStorageUtil fileStorageUtil,
                           @Qualifier("generalAuthenticationService")
                                   AuthenticationService authenticationService,
                           ProfileRepository profileRepository){
        this.filesRepository = repository;
        this.fileStorageUtil = fileStorageUtil;
        this.authenticationService = authenticationService;
        this.profileRepository = profileRepository;
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
        filesRepository.delete(file);
        String path = storagePath + "/"+ file.getEncodedName();
        File storagedFile = new File(path);

        if(storagedFile.delete()){
            System.out.println("!!/n!!!!/n!!"+ storagedFile.getName() + " is deleted!");
        }else{
            System.out.println("!!/n!!!!/n!!" + "Delete operation is failed.");
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


    @Override
    public String savePhoto(MultipartFile file, Authentication authentication) {
        FileOfService fileOfService = fileStorageUtil.convertFromMultipart(file);

        filesRepository.save(fileOfService);

        User user = authenticationService.getUserByAuthentication(authentication);
        Profile profile = user.getProfile();

        if (profile.getAvatar() != null) {
            delete(profile.getAvatar());
        }
        profile.setAvatar(fileOfService);
        profileRepository.save(profile);

        fileStorageUtil.copyToStorage(file, fileOfService.getEncodedName());

        return fileOfService.getEncodedName();
    }
}
