package io.vscale.uniservice.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView getIndexPage(){

        return new ModelAndView("user/index");

    }

}
