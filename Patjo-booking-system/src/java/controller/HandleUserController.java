package controller;

import java.util.List;
import model.BookingDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * Displays a view for managing user bookings
     *
     * @param model The Spring Model to add attributes for the view.
     * @return
     */
    @RequestMapping("/users/managebooking")
    public String showUsers(Model model) {
        List<User> users = userService.getAllStudentUsers();
        model.addAttribute("userList", users);

        return "manageBookingView";
    }

    /**
     * Displays a view for managing a specific user's bookings, showing both
     * active bookings and available time slots.
     *
     * @param userId The ID of the user
     * @param userName The name of the user
     * @param model The Spring Model to add attributes for the view.
     * @return
     */
    @RequestMapping("/users/showuserbooking")
    public String manageUserBooking(@RequestParam("userId") Integer userId,
            @RequestParam("username") String userName,
            Model model) {

        List<BookingDTO> userBookings = userService.getUsersActiveBookings(userId);
        List<BookingDTO> bookingList = userService.getAvailableTimeSlots(userId);

        model.addAttribute("userBookings", userBookings);
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "userBookingView";

    }

    /**
     * Deletes a specific time slot for a user
     *
     * @param userId The ID of the user
     * @param userName The name of the user
     * @param timeSlotId The ID of the time slot to be deleted.
     * @param model The Spring Model to add attributes for the view.
     * @return A redirect to the "showuserbooking" view.
     */
    @RequestMapping("/users/deletetimeslot")
    public String deleteUserTimeSlot(@RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName,
            @RequestParam("selectedTimeSlot") Integer timeSlotId, Model model) {

        userService.deleteUserBooking(userId, timeSlotId);

        model.addAttribute("username", userName);
        model.addAttribute("userId", userId);
        return "redirect:/users/showuserbooking";

    }

    /**
     * Assigns a specific time slot to a user
     *
     * @param userId The ID of the user
     * @param userName The name of the user.
     * @param timeSlotId The ID of the time slot to be assigned.
     * @param model The Spring Model to add attributes for the view.
     * @return A redirect to the "showuserbooking" view.
     */
    @RequestMapping("/users/assigntimeslot")
    public String assignTimeSlotToUser(@RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName,
            @RequestParam("selectedTimeSlot") Integer timeSlotId, Model model) {

        userService.assignTimeSlotToUser(userId, timeSlotId);

        model.addAttribute("username", userName);
        model.addAttribute("userId", userId);

        return "redirect:/users/showuserbooking";
    }
}
