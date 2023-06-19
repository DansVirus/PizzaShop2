package gr.aueb.cf.pizzashop2.service;

import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.model.User;
import gr.aueb.cf.pizzashop2.repository.UserRepository;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User registerUser(UserDTO userToRegister) {
        return userRepository.save(convertToUser(userToRegister));
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDTO) throws EntityNotFoundException {
        User user = userRepository.findUserById(userDTO.getId());
        if (user == null) throw new EntityNotFoundException(User.class, userDTO.getId());
        return userRepository.save(convertToUser(userDTO));
    }


    @Transactional
    @Override
    public void deleteUser(Long id)  {
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public List<User> getUsersByUsername(String username) throws EntityNotFoundException {
        List<User> users;
        users = userRepository.findUsersByUsername(username);
        System.out.println("users: " + users);
        if (users.size() == 0) throw new EntityNotFoundException(User.class, 0L);
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user;
        user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException(User.class, 0L);
        return user.get();
    }
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean usernameExists(String email) {
        return false;
    }

    private static User convertToUser(UserDTO dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword());
    }
}
