package io.vscale.uniservice.controllers.general.admin;

import lombok.AllArgsConstructor;
import io.vscale.uniservice.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {

    private UserService userService;

    @GetMapping("/index")
    public ModelAndView getAdminPage(){
        return new ModelAndView("index/admin");
    }

    @GetMapping("/get_all_users")
    public ModelAndView getAllUsers(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index/admin");
        modelAndView.addObject("users", this.userService.getAllUsers());

        return modelAndView;

    }

    @GetMapping("/get_user_by_id/{user_id}")
    public ModelAndView getUserById(@PathVariable("user_id") Long userId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index/admin");
        modelAndView.addObject("user", this.userService.getUserById(userId));

        return modelAndView;

    }

}
