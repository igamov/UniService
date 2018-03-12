package io.vscale.uniservice.services.interfaces.admin;

import io.vscale.uniservice.forms.*;


/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface UserAdminService {

    void addNewUser(NewUserForm newUserForm);
    void addProfileToUser(ProfileForm profileForm);
    void makeStudent(StudentForm studentForm);
    void makeCooperator(CooperatorForm cooperatorForm);
    void addGroup(GroupForm groupForm);

}
