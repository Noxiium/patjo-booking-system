package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LoginRepository;

/**
 *
 * @author patricialagerhult
 */
@Service
public class LoginService {
    
     private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public User handleUserLogin(String username, String password) {
        User user = loginRepository.findByUsername(username);

        System.out.println(user);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return user ID to the controller
        }
        return null; // Return null if user doesn't exist or password doesn't match
    }

}
