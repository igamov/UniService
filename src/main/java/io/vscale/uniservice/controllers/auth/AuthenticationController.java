package io.vscale.uniservice.controllers.auth;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.security.role.Role;
import io.vscale.uniservice.services.interfaces.auth.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(@ModelAttribute("model") ModelMap model, Authentication authentication,
                        @RequestParam Optional<String> error) {

        if (authentication != null) {
            return "redirect:/authorize";
        }

        model.addAttribute("error", error);

        return "auth/login";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication) {

        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";

    }

    @GetMapping("/authorize")
    public String root(Authentication authentication){

        if(authentication != null){

            User user = this.authenticationService.getUserByAuthentication(authentication);

            if(user.getRoles().size() > 1){

                return "redirect:/choose_role";

            }else{

                RoleType roleType = user.getRoles().iterator().next();

                if(roleType.getRole().equals(Role.ADMIN)){
                    return "redirect:/admin/index";
                }else if(roleType.getRole().equals(Role.STUDENT)){
                    return "redirect:/student/index";
                }else if(roleType.getRole().equals(Role.COOPERATOR)){
                    return "redirect:/cooperator/index";
                }

            }

        }

        return "redirect:/login";

    }

    @GetMapping("/choose_role")
    public String redirectByRole(Authentication authentication, @RequestParam("role") String role){

        if(authentication != null){

            Role[] roles = Role.values();

            boolean roleCheck = Arrays.stream(roles)
                                      .anyMatch(role1 -> role1.toString().equals(role));

            if(!roleCheck){
                return "redirect:/login";
            }else{

                StringBuilder sb = new StringBuilder();
                sb.append("redirect:/").append(role).append("/index");
                return sb.toString();

            }

        }

        return "redirect:/login";

    }

}
