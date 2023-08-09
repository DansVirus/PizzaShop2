package gr.aueb.cf.pizzashop2.controller;

import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.model.User;
import gr.aueb.cf.pizzashop2.service.IUserService;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.pizzashop2.service.util.LoggerUtil;
import gr.aueb.cf.pizzashop2.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user_admin")
public class AdminController {

    private final IUserService userService;
    private final UserValidator userValidator;



    @Autowired
    public AdminController(IUserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/api/users")
    public String getUsers(Model model) throws EntityNotFoundException {
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "user_admin";
    }

    @GetMapping("/api/users/{userId}")
    public String updateUserForm(@PathVariable("userId") Long id, Model model) throws EntityNotFoundException {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        // Return the view for editing the user (e.g., "edit_user.html")
        return "edit_user";
    }

    @PostMapping("/api/users/{userId}")
    public String updateUser(@PathVariable("userId") Long id, @ModelAttribute("user") UserDTO updatedUserDTO) throws EntityNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, id);
        }

        // Update the user's properties
        user.setUsername(updatedUserDTO.getUsername());
        user.setPassword(updatedUserDTO.getPassword());

        userService.updateUser(updatedUserDTO);

        // Redirect to the user list page after updating the user
        return "redirect:/user_admin/api/users";
    }


    @DeleteMapping("/api/users/{userId}")
    public String deleteUser(@PathVariable("userId") Long id) throws EntityNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, id);
        }else {
            userService.deleteUser(id);
            System.out.println("user" + deleteUser(id));
        }
        // Redirect to the user list page after deleting the user
        return "redirect:/user_admin/api/users";
    }

    @PostMapping("/api/users")
    public String addUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            LoggerUtil.getCurrentLogger().warning(("empty"));
            return "add_user";
        }
        User user = userService.registerUser(userDTO);
        return "redirect:/user_admin/api/users";
    }

    @GetMapping("/admin/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "add_user";
    }



}



