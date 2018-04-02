package io.vscale.uniservice.services.implementations.student;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.repositories.data.StudentRepository;
import io.vscale.uniservice.services.interfaces.student.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long studentId) {
        return this.studentRepository.findOne(studentId);
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return this.studentRepository.findAll(pageable);
    }
}