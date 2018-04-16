package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.FileOfService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface FileOfServiceRepository extends JpaRepository<FileOfService, Long> {
    List<FileOfService> findAllByType(String type);
    FileOfService findOneById(Long id);
    FileOfService findOneByEncodedName(String fileName);
    FileOfService findByEventsContaining(Event event);
}
