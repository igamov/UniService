package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.*;
import io.vscale.uniservice.forms.*;
import io.vscale.uniservice.repositories.*;
import lombok.AllArgsConstructor;

import io.vscale.uniservice.security.states.UserState;
import io.vscale.uniservice.services.interfaces.admin.UserAdminService;
import io.vscale.uniservice.services.interfaces.auth.RoleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAdminServiceImpl implements UserAdminService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private StudentRepository studentRepository;
    private CooperatorRepository cooperatorRepository;
    private GroupRepository groupRepository;
    private RoleTypeService roleTypeService;

    @Override
    public void addNewUser(NewUserForm newUserForm) {

        Optional<RoleType> roleType = this.roleTypeService.getRoleTypeByRole(newUserForm.getRole());

        if(!roleType.isPresent()){
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                        .login(newUserForm.getLogin())
                        .password(newUserForm.getPassword())
                        .state(UserState.CONFIRMED)
                        .build();

        this.userRepository.save(user);

    }

    @Override
    public void addProfileToUser(ProfileForm profileForm) {

        User user = this.userRepository.findOne(profileForm.getUserId());

        Profile profile = Profile.builder()
                                 .surname(profileForm.getSurname())
                                 .name(profileForm.getName())
                                 .patronymic(profileForm.getPatronymic())
                                 .email(profileForm.getEmail())
                                 .description(profileForm.getDescription())
                                 .user(user)
                                 .build();

        this.profileRepository.save(profile);

    }

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

    @Override
    public void makeCooperator(CooperatorForm cooperatorForm) {

        Profile profile = this.profileRepository.findOne(cooperatorForm.getProfileId());

        Cooperator cooperator = Cooperator.builder()
                                          .profile(profile)
                                          .recordOfService(cooperatorForm.getRecordOfService())
                                          .appointment(cooperatorForm.getAppointment())
                                          .build();

        this.cooperatorRepository.save(cooperator);

    }

    @Override
    public void addGroup(GroupForm groupForm) {

        Group group = Group.builder()
                           .title(groupForm.getTitle())
                           .creationDate(Timestamp.valueOf(groupForm.getCreationDate()))
                           .build();

        this.groupRepository.save(group);

    }
}
