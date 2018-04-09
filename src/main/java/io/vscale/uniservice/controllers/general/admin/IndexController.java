package io.vscale.uniservice.controllers.general.admin;

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
@Controller("AdminIndexController")
@RequestMapping("/admin")
public class IndexController {

    @GetMapping("/index")
    public ModelAndView getAdminPage(){
        return new ModelAndView("redirect:/admin/students");
    }

}
