package io.vscale.uniservice.services.interfaces.events;

import io.vscale.uniservice.domain.ConfirmationLimits;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface ConfirmationLimitsService {
    List<ConfirmationLimits> findAllBySemesterNumber(Long semesterNumber);
}
