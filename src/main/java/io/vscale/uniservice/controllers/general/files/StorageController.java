package io.vscale.uniservice.controllers.general.files;

import io.vscale.uniservice.services.interfaces.files.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 14.04.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StorageController {

    private FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<String> savePhoto(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String filePath = fileService.savePhoto(file, authentication);
        return ResponseEntity
                .ok()
                .body(filePath);
    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName,
                        HttpServletResponse response) {
        fileService.writeFileToResponse(fileName, response);
    }
}
