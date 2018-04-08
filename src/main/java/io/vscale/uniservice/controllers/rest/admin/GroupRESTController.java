package io.vscale.uniservice.controllers.rest.admin;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.forms.rest.GroupForm;
import io.vscale.uniservice.services.interfaces.admin.GroupAdminService;
import io.vscale.uniservice.services.interfaces.student.GroupService;
import io.vscale.uniservice.validators.GroupFormValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 17.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/admin_role")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRESTController {

    private GroupFormValidator groupFormValidator;
    private GroupAdminService groupAdminService;
    private GroupService groupService;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
         binder.addValidators(this.groupFormValidator);
    }

    @PostMapping("/add_group")
    public List<Group> addStudentsGroup(@RequestBody @Valid @ModelAttribute("groupForm")GroupForm groupForm){
        this.groupAdminService.addGroupREST(groupForm);
        return this.groupService.getAllGroups();
    }

}
