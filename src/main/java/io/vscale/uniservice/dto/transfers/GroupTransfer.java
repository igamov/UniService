package io.vscale.uniservice.dto.transfers;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.dto.domain.GroupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 19.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class GroupTransfer {

    public List<GroupDTO> getTransferGroups(List<Group> groups){

        List<GroupDTO> resultList = new ArrayList<>();

        groups.forEach(group -> {

            Set<Student> students = group.getStudents();

            students.forEach(student ->
                    resultList.add(GroupDTO.builder()
                                           .id(group.getId())
                                           .title(group.getTitle())
                                           .creationDate(group.getCreationDate())
                                           .studentId(student.getId())
                                           .build()));

        });

        return resultList;

    }

}
