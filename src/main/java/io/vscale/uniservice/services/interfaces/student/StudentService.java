package io.vscale.uniservice.services.interfaces.student;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface StudentService {

    List<Student> getStudentsByGroup(Group group);
    List<Student> getAllStudents();
    Student getStudentById(Long studentId);
    Page<Student> findAll(Pageable pageable);

}
