package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
        Integer userId = userRepository.fetchUserID(user);
        user.setUserId(userId);


    }
    
    public User handleUserLogin(String username, String password) {
    User user = userRepository.findByUsername(username);
    
    System.out.println(user);
    if (user != null && user.getPassword().equals(password)) {
        return user; // Return user ID to the controller
    }
    return null; // Return null if user doesn't exist or password doesn't match
    }

}
