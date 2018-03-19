package io.vscale.uniservice.dto.transfers;

import io.vscale.uniservice.domain.Cooperator;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.dto.domain.CooperatorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 19.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class CooperatorTransfer {

    public List<CooperatorDTO> getTransferCooperators(List<Cooperator> cooperators){

        List<CooperatorDTO> resultList = new ArrayList<>();

        cooperators.forEach(cooperator -> {

            Set<FileOfService> files = cooperator.getCooperatorFiles();

            files.forEach(file ->
                    resultList.add(CooperatorDTO.builder()
                                                .id(cooperator.getId())
                                                .recordOfService(cooperator.getRecordOfService())
                                                .appointment(cooperator.getAppointment())
                                                .profileId(cooperator.getProfile().getId())
                                                .fileId(file.getId())
                                                .build()));

        });

        return resultList;

    }

}
