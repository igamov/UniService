package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.ExcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 16.04.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface ExcelEntityRepository extends JpaRepository<ExcelEntity, Long> {
}
