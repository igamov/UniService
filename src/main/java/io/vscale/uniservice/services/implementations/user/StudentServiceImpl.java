package io.vscale.uniservice.services.implementations.user;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.repositories.StudentRepository;
import io.vscale.uniservice.services.interfaces.user.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudentsByGroup(Group group) {

        List<Student> currentStudents = this.studentRepository.findAll();

        return currentStudents.stream()
                              .filter(student -> student.getGroups().equals(group))
                              .collect(Collectors.toList());

    }
}