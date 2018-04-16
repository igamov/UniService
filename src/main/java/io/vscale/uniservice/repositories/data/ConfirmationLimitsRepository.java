package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.ConfirmationLimits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface ConfirmationLimitsRepository extends JpaRepository<ConfirmationLimits, Long> {
    List<ConfirmationLimits> findAllBySemesterNumber(Long semesterNumber);
}
