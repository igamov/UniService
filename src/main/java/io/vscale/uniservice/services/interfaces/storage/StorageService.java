package io.vscale.uniservice.services.interfaces.storage;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface StorageService {
    String savePhoto(MultipartFile multipartFile, Authentication authentication);
    void writeFileToResponse(String fileName, HttpServletResponse response);
}
