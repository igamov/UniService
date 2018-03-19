package io.vscale.uniservice.dto.transfers;

import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.dto.domain.ConfirmationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 19.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class ConfirmationTransfer {

    public List<ConfirmationDTO> getTransferConfirmations(List<Confirmation> confirmations){

        List<ConfirmationDTO> resultList = new ArrayList<>();

        confirmations.forEach(confirmation -> {

            Set<Student> students = confirmation.getStudents();

            students.forEach(student ->
                    resultList.add(ConfirmationDTO.builder()
                                                  .id(confirmation.getId())
                                                  .description(confirmation.getDescription())
                                                  .profileId(confirmation.getProfile().getId())
                                                  .fileId(confirmation.getFileOfService().getId())
                                                  .studentId(student.getId())
                                                  .build()));

        });

        return resultList;

    }

}
