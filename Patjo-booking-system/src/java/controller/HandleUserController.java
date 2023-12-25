package controller;

import java.util.List;
import model.BookingDTO;
import model.PresentationListDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            @RequestParam(defaultValue = "false") boolean isAdmin,
            Model model) {
        // Create User object and save to database using UserService or UserRepository
        try {
            userService.addNewUser(username, password, isAdmin);
        } catch (DuplicateKeyException dke) {
            model.addAttribute("errorMessage", "Username already exists. Please choose a different username.");
            //TODO Handle the errorMessage in handleUserView so the user see the error.
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users);

        // Redirect to the same page after successful addition
        return "redirect:/users/showusers"; // Replace 'adminPage' with your actual page URL
    }

    /**
     * Handles the request to remove selected users and their associated
     * bookings.
     *
     * @param userIds String of user IDs to be removed.
     * @param model The model for adding attributes.
     * @param redirectAttributes Used to flash attributes for the redirect.
     * @return Redirects to the handleUsersView after removing users.
     */
    @PostMapping
    @RequestMapping("/users/removeUsers")
    public String removeUsers(@RequestParam("userIds") String userIds, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("removeUsers - POST");
        String[] selectedUserIds = userIds.split(",");

        userService.removeUserAndAssociatedBookings(selectedUserIds);
        redirectAttributes.addFlashAttribute("deleteduser", "deleteduser");
        return "redirect:/users/showusers";
    }

    @RequestMapping("/users/managebooking")
    public String showUsers(Model model) {
        List<User> users = userService.getAllStudentUsers();
        model.addAttribute("userList", users);

        return "manageBookingView";
    }

    @RequestMapping("/users/showuserbooking")
    public String manageUserBooking(@RequestParam("userId") Integer userId, 
                                    @RequestParam("username") String userName, 
                                    Model model) {
        System.out.println("userid: " + userId);

        List<BookingDTO> userBookings = userService.getUsersActiveBookings(userId);
        List<BookingDTO> bookingList = userService.getAvailableTimeSlots(userId);

        model.addAttribute("userBookings", userBookings);
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "userBookingView";

    }
    
    @RequestMapping("/users/deletetimeslot")
    public String deleteUserTimeSlot(@RequestParam("userId") Integer userId,
                                    @RequestParam("userName") String userName,
                                    @RequestParam("selectedTimeSlot") Integer timeSlotId, Model model){
        
        System.out.println(userId);
        System.out.println(userName);
        System.out.println(timeSlotId);
        
        model.addAttribute("username", userName);
        model.addAttribute("userId", userId);
        return "redirect:/users/showuserbooking";
    
    }
}
