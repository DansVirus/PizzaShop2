package gr.aueb.cf.pizzashop2.controller;

import gr.aueb.cf.pizzashop2.authentication.CustomAuthenticationSuccessHandler;
import gr.aueb.cf.pizzashop2.model.User;
import gr.aueb.cf.pizzashop2.service.IUserService;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    private final IUserService userService;

    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/login")
    String login(Model model, Principal principal, HttpServletRequest request) throws Exception {

        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);

        return principal == null ? "login" : "redirect:/api/users";

    }

    @GetMapping(path = "/")
    String root(Model model, Principal principal, HttpServletRequest request) throws Exception  {
        return principal == null ? "login" : "/users";
    }




}
