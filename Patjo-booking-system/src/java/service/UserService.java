package service;

import java.util.ArrayList;
import java.util.List;
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

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public void addNewUser(String username, String password, Boolean isAdmin) {
        int isAdminInt = 0;
        if (isAdmin) {
            isAdminInt = 1;
        }
        User newUser = new User(username, password, isAdminInt);
        saveUser(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.fetchAllUsersFromDB();
    }

    public List<User> getAllAdminUsersFromExistingList(List<User> allUsers) {
        List<User> adminUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getIsAdmin() == 1) {
                adminUsers.add(user);
            }
        }
        return adminUsers;
    }

    public List<User> getAllNonAdminUsersFromExistingList(List<User> allUsers) {
        List<User> nonAdminUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getIsAdmin() == 0) {
                nonAdminUsers.add(user);
            }
        }
        return nonAdminUsers;
    }

    public List<User> tempGetAllUsers() {

        // TODO Actually fetch from DATABASE using userRepository
        List<User> list = new ArrayList<>();
        User user1 = new User("johan@kth.se", "1234", 1);
        user1.setUserId(4);
        list.add(user1);

        User user2 = new User("namn7@kth.se", "1234", 0);
        user2.setUserId(5);
        list.add(user2);

        User user3 = new User("namn2@kth.se", "1234", 0);
        user3.setUserId(6);
        list.add(user3);

        for (User user : list) {
            System.out.print(user);
        }
        return list;
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
