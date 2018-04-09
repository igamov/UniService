package io.vscale.uniservice.controllers.general.student;

import io.vscale.uniservice.services.interfaces.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {

    private UserService userService;

    @GetMapping("/index")
    public ModelAndView getProfilePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student/index");
        modelAndView.addObject("user", userService.findByAuthentication(authentication));
        return modelAndView;
    }

    @PostMapping("/save-photo")
    public ModelAndView editUserProfile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        userService.savePhoto(file, authentication);

        return  new ModelAndView("redirect:/student/index");
    }
}
