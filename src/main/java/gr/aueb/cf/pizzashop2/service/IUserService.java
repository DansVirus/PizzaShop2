package gr.aueb.cf.pizzashop2.service;

import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.model.User;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {
    User registerUser(UserDTO userToRegister);
    User updateUser(UserDTO userDTO) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;
    List<User> getUsersByUsername(String username) throws EntityNotFoundException;

    Iterable<User> getAllUsers();
    User getUserById(Long id) throws EntityNotFoundException;
    boolean usernameExists(String username);
}
