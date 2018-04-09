package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.forms.rest.StudentForm;
import io.vscale.uniservice.repositories.data.GroupRepository;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import io.vscale.uniservice.repositories.data.StudentRepository;
import io.vscale.uniservice.services.interfaces.admin.StudentAdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 17.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentAdminServiceImpl implements StudentAdminService{

    private ProfileRepository profileRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Override
    public void makeStudent(StudentForm studentForm) {

        Profile profile = this.profileRepository.findOne(studentForm.getProfileId());

        Group group = this.groupRepository.findByTitle(studentForm.getGroupTitle())
                                          .orElseThrow(IllegalArgumentException::new);

        Student student = Student.builder()
                                 .profile(profile)
                                 .gender(studentForm.getGender())
                                 .course(studentForm.getCourse())
                                 .groups(Collections.singleton(group))
                                 .build();

        this.studentRepository.save(student);

    }
}
