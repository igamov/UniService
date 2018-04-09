package io.vscale.uniservice.controllers.general.admin;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.forms.rest.NewUserForm;
import io.vscale.uniservice.services.interfaces.admin.UserAdminService;
import io.vscale.uniservice.validators.NewUserFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private UserAdminService userAdminService;
    private NewUserFormValidator newUserFormValidator;

    @GetMapping("/add_user")
    public String getPage(){
        return "admin/add_user";
    }

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.newUserFormValidator);
    }

    @PostMapping("/add_user")
    public String addNewUser(@Valid @ModelAttribute("newUserForm") NewUserForm newUserForm,
                             BindingResult errors, RedirectAttributes attributes){

        if(errors.hasErrors()){
            attributes.addFlashAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/register";
        }

        this.userAdminService.addNewUserREST(newUserForm);

        return "admin/success_page";

    }

}
