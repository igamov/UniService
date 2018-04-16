package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.ExcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelEntityRepository extends JpaRepository<ExcelEntity, Long> {
}
