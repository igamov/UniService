package io.vscale.uniservice.controllers.rest.admin;

import io.vscale.uniservice.domain.Cooperator;
import io.vscale.uniservice.forms.rest.CooperatorForm;
import io.vscale.uniservice.services.interfaces.admin.CooperatorAdminService;
import io.vscale.uniservice.services.interfaces.cooperator.CooperatorService;
import io.vscale.uniservice.validators.CooperatorFormValidator;
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
public class CooperatorRESTController {

    private CooperatorFormValidator cooperatorFormValidator;
    private CooperatorAdminService cooperatorAdminService;
    private CooperatorService cooperatorService;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.cooperatorFormValidator);
    }

    @PostMapping("/create_cooperator")
    public List<Cooperator> createCooperator(@RequestBody @Valid @ModelAttribute("cooperatorForm")
                                                     CooperatorForm cooperatorForm){
        this.cooperatorAdminService.makeRESTCooperator(cooperatorForm);
        return this.cooperatorService.getAllCooperators();
    }

}
