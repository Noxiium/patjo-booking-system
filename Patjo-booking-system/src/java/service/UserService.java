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
        user.setId(userId);

        System.out.println(user.getId());

    }

    public void handleUserLogin(User user) {

        Integer userId = userRepository.checkIfUserExistInDB(user);
        //TODO method, check if password match
        
        if (userId == -1) {
        //TODO send view, ERROR
        } else {
            user.setId(userId);
        }
    }

}
