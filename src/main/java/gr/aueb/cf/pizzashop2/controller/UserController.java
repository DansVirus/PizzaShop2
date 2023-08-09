package gr.aueb.cf.pizzashop2.controller;

import gr.aueb.cf.pizzashop2.model.User;
import gr.aueb.cf.pizzashop2.service.IUserService;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {

        this.userService = userService;
    }

    @GetMapping("/api/users")
    public String getUsers(Model model) throws EntityNotFoundException {
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";

    }

}

