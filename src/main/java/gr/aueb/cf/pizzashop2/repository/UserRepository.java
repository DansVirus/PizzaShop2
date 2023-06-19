package gr.aueb.cf.pizzashop2.repository;

import gr.aueb.cf.pizzashop2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


// This interface is used to define the database operations for the User model.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameEquals(String username);


    User findUserById(Long id);


    List<User> findUsersByUsername(String username);

    @Query("SELECT count(U.id) > 0 FROM User U WHERE U.username = ?1 and U.password = ?2")
    boolean isUserValid(String username, String password);

    @Query("SELECT count(U.id) > 0 FROM User U WHERE U.username = ?1")
    boolean usernameExists(String username);

}
