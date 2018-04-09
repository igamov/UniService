package io.vscale.uniservice.services.implementations.student;

import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.forms.general.StudentForm;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import io.vscale.uniservice.repositories.indexing.StudentESRepository;
import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.repositories.data.StudentRepository;
import io.vscale.uniservice.services.interfaces.student.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    private ProfileRepository profileRepository;
    private StudentESRepository studentESRepository;

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

    @Override
    public Page<Student> retrieveSortedStudentsAsc(Pageable pageable) {

        List<Student> students = this.studentRepository.findAllByOrderBySurnameAsc();

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > students.size() ? students.size() : (start + pageable.getPageSize());

        return new PageImpl<>(students.subList(start, end), pageable, students.size());

    }

    @Override
    public Page<Student> retrieveSortedStudentsDesc(Pageable pageable) {

        List<Student> students = this.studentRepository.findAllByOrderBySurnameDesc();

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > students.size() ? students.size() : (start + pageable.getPageSize());

        return new PageImpl<>(students.subList(start, end), pageable, students.size());

    }

    @Override
    public void addStudent(StudentForm studentForm) {

        Profile profile = this.profileRepository.findOne(studentForm.getProfileId());

        Student student = Student.builder()
                                 .profile(profile)
                                 .course(studentForm.getCourse())
                                 .gender(studentForm.getGender())
                                 .headOrganizations(new HashSet<>())
                                 .events(new ArrayList<>())
                                 .confirmations(new HashSet<>())
                                 .groups(new HashSet<>())
                                 .build();

        this.studentRepository.save(student);
        this.studentESRepository.save(student);

    }

    @Override
    public void updateStudent(StudentForm studentForm) {

        Student student = this.studentRepository.findOne(studentForm.getId());
        Profile profile = this.profileRepository.findOne(studentForm.getProfileId());

        student.setCourse(studentForm.getCourse());
        student.setGender(studentForm.getGender());
        student.setProfile(profile);

        this.studentRepository.save(student);
        this.studentESRepository.save(student);


    }

    @Override
    public void deleteStudent(StudentForm studentForm) {

        this.studentRepository.delete(studentForm.getId());
        this.studentESRepository.delete(String.valueOf(studentForm.getId()));

    }
}