package io.vscale.uniservice.services.interfaces.user;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface StudentService {

    List<Student> getStudentsByGroup(Group group);

}
