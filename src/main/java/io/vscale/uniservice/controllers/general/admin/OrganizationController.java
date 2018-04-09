package io.vscale.uniservice.controllers.general.admin;

import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.services.interfaces.events.OrganizationService;
import io.vscale.uniservice.utils.PageWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 26.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController {

    private OrganizationService organizationService;

    @GetMapping("/organizations")
    public ModelAndView showOrganizations(@PageableDefault(value = 4) Pageable pageable){

        PageWrapper<Organization> pageWrapper =
                new PageWrapper<>(this.organizationService.findAll(pageable), "/admin/organizations");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-organizations");
        modelAndView.addObject("pageWrapper", pageWrapper);

        return modelAndView;

    }

}
