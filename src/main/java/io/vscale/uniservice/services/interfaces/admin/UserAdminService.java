package io.vscale.uniservice.services.interfaces.admin;

import io.vscale.uniservice.forms.NewUserForm;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface UserAdminService {

    void addNewUser(NewUserForm newUserForm);

}
