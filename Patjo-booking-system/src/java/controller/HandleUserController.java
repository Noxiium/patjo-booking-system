/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

/**
 *
 * @author PC
 */
@Controller
public class HandleUserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all users from the database and adds them to the model.
     *
     * @param model the model to add the user list to
     * @return the name of the view to handle the users
     */
    @GetMapping
    @RequestMapping("users/showusers")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        List<User> admins = userService.getAllAdminUsersFromExistingList(users);
        List<User> nonAdmins = userService.getAllNonAdminUsersFromExistingList(users);
        model.addAttribute("adminList", admins);
        model.addAttribute("nonAdminList", nonAdmins);// Fetch all users from the database
        return "handleUsersView";
    }
    
    // Endpoint to add user
    @PostMapping
    @RequestMapping("/users/addUser")
    public String addUser(@RequestParam String username, 
                          @RequestParam String password, 
                          @RequestParam(defaultValue = "false") 
                          boolean isAdmin, 
                          Model model) {
        // Create User object and save to database using UserService or UserRepository
        try{
            userService.addNewUser(username, password, isAdmin);
        } catch (DuplicateKeyException dke){
            model.addAttribute("errorMessage", "Username already exists. Please choose a different username.");
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        
        
        // Redirect to the same page after successful addition
        return "redirect:/users/showusers"; // Replace 'adminPage' with your actual page URL
    }
    
    
    @PostMapping
    @RequestMapping("/users/removeUsers")
    public String removeUsers(@RequestParam("userIds") String userIds, Model model) {
        System.out.println("removeUsers - POST");
        String[] selectedUserIds = userIds.split(",");
        for (String userId : selectedUserIds) {
            System.out.println(userId);
        }
        
        return "redirect:/users/showusers";
    }
}
