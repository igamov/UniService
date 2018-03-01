package io.vscale.uniservice.services.interfaces.files;

import io.vscale.uniservice.domain.FileOfService;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface FileService {
    List<FileOfService> findAllByType(String type);
}
