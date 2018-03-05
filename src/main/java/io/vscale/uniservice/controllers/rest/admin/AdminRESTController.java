package io.vscale.uniservice.controllers.rest.admin;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.NewUserForm;
import io.vscale.uniservice.services.interfaces.admin.UserAdminService;
import io.vscale.uniservice.services.interfaces.user.UserService;
import io.vscale.uniservice.validators.NewUserFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminRESTController {

    private UserAdminService userAdminService;
    private NewUserFormValidator newUserFormValidator;
    private UserService userService;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.newUserFormValidator);
    }

    @PostMapping("/add_user")
    public User addNewUser(@RequestBody @Valid @ModelAttribute("newUserForm") NewUserForm newUserForm){
        this.userAdminService.addNewUser(newUserForm);
        return this.userService.getUserByLogin(newUserForm.getLogin());
    }

}
