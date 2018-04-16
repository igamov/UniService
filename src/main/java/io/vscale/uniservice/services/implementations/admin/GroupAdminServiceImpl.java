package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.forms.rest.GroupForm;
import io.vscale.uniservice.repositories.data.GroupRepository;
import io.vscale.uniservice.services.interfaces.admin.GroupAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 17.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class GroupAdminServiceImpl implements GroupAdminService{

    private final GroupRepository groupRepository;

    @Autowired
    public GroupAdminServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void addGroupREST(GroupForm groupForm) {

        Group group = Group.builder()
                           .title(groupForm.getTitle())
                           .creationDate(Timestamp.valueOf(groupForm.getCreationDate()))
                           .build();

        this.groupRepository.save(group);

    }
}
