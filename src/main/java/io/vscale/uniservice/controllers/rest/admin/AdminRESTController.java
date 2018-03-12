package io.vscale.uniservice.controllers.rest.admin;

import io.vscale.uniservice.domain.*;
import io.vscale.uniservice.forms.*;
import io.vscale.uniservice.services.interfaces.cooperator.CooperatorService;
import io.vscale.uniservice.services.interfaces.student.GroupService;
import io.vscale.uniservice.services.interfaces.student.StudentService;
import io.vscale.uniservice.services.interfaces.user.ProfileService;
import io.vscale.uniservice.validators.*;
import lombok.AllArgsConstructor;

import io.vscale.uniservice.services.interfaces.admin.UserAdminService;
import io.vscale.uniservice.services.interfaces.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/admin_role")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminRESTController {

    private NewUserFormValidator newUserFormValidator;
    private StudentFormValidator studentFormValidator;
    private ProfileFormValidator profileFormValidator;
    private CooperatorFormValidator cooperatorFormValidator;
    private GroupFormValidator groupFormValidator;

    private UserAdminService userAdminService;
    private UserService userService;
    private ProfileService profileService;
    private StudentService studentService;
    private CooperatorService cooperatorService;
    private GroupService groupService;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.newUserFormValidator, this.studentFormValidator, this.profileFormValidator,
                             this.cooperatorFormValidator, this.groupFormValidator);
    }

    @PostMapping("/add_user")
    public User addNewUser(@RequestBody @Valid @ModelAttribute("newUserForm") NewUserForm newUserForm){
        this.userAdminService.addNewUser(newUserForm);
        return this.userService.getUserByLogin(newUserForm.getLogin());
    }

    @GetMapping("/get_user/{user_id}")
    public User getUser(@PathVariable("user_id") Long userId){
        return this.userService.getUserById(userId);
    }

    @GetMapping("/get_all_users")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @PostMapping("/attache_profile")
    public List<Profile> attachProfile(@RequestBody @Valid @ModelAttribute("profileForm")ProfileForm profileForm){
        this.userAdminService.addProfileToUser(profileForm);
        return this.profileService.getAllProfiles();
    }

    @PostMapping("/create_student")
    public List<Student> createStudent(@RequestBody @Valid @ModelAttribute("studentForm")StudentForm studentForm){
        this.userAdminService.makeStudent(studentForm);
        return this.studentService.getAllStudents();
    }

    @PostMapping("/create_cooperator")
    public List<Cooperator> createCooperator(@RequestBody @Valid @ModelAttribute("cooperatorForm")
                                                         CooperatorForm cooperatorForm){
        this.userAdminService.makeCooperator(cooperatorForm);
        return this.cooperatorService.getAllCooperators();
    }

    @PostMapping("/add_group")
    public List<Group> addStudentsGroup(@RequestBody @Valid @ModelAttribute("groupForm")GroupForm groupForm){
        this.userAdminService.addGroup(groupForm);
        return this.groupService.getAllGroups();
    }

}
