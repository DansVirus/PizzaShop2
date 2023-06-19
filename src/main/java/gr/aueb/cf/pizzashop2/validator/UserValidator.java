package gr.aueb.cf.pizzashop2.validator;

import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final IUserService userService;

    @Autowired
    public UserValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userToRegister = (UserDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (userToRegister.getUsername().length() < 3 || userToRegister.getUsername().length() > 32) {
            errors.rejectValue("username", "size");
        }
        if (userService.usernameExists(userToRegister.getUsername())) {
            errors.rejectValue("email", "duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (userToRegister.getPassword().length() < 3 || userToRegister.getPassword().length() > 32) {
            errors.rejectValue("password", "size");
        }

//        if (!Objects.equals(userToRegister.getPassword(), userToRegister.getConfirmPassword())) {
//            errors.rejectValue("confirmPassword", "confirmation");
//        }
    }
}
