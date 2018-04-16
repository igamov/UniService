package io.vscale.uniservice.services.implementations.student;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.repositories.data.GroupRepository;
import io.vscale.uniservice.services.interfaces.student.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        return this.groupRepository.findAll();
    }
}
