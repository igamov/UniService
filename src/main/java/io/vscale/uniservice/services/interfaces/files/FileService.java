package io.vscale.uniservice.services.interfaces.files;

import io.vscale.uniservice.domain.FileOfService;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface FileService {
    List<FileOfService> findAllByType(String type);
    void save(FileOfService fileOfService);
    FileOfService findById(Long id);
    String save(MultipartFile multipartFile);
    void delete(FileOfService file);
    void writeFileToResponse(String fileName, HttpServletResponse response);

    String savePhoto(MultipartFile file, Authentication authentication);
}
