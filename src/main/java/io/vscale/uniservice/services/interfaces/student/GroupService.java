package io.vscale.uniservice.services.interfaces.student;

import io.vscale.uniservice.domain.Group;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface GroupService {

    List<Group> getAllGroups();

}
