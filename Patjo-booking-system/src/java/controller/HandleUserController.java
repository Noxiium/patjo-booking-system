package controller;

import java.util.Arrays;
import java.util.List;
import model.BookingDTO;
import model.CourseDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.HandleUserService;

/**
 *
 * @author PC
 */
@Controller
public class HandleUserController {

    @Autowired
    private HandleUserService handleUserService;

    /**
     * Retrieves all users from the database and adds them to the model.
     *
     * @param model the model to add the user list to
     * @return the name of the view to handle the users
     */
    @GetMapping
    @RequestMapping("users/showusers")
    public String getAllUsers(Model model) {
        List<User> users = handleUserService.getAllUsers();
        List<User> admins = handleUserService.getAllAdminUsersFromExistingList(users);
        List<User> nonAdmins = handleUserService.getAllNonAdminUsersFromExistingList(users);
        model.addAttribute("adminList", admins);
        model.addAttribute("nonAdminList", nonAdmins);// Fetch all users from the database
        
        List<CourseDTO> courseList = handleUserService.fetchAvailableCourses();
        model.addAttribute("courseList", courseList);
        return "handleUsersView";
    }

    // Endpoint to add user
    @PostMapping
    @RequestMapping("/users/addUser")
    public String addUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String courseId,
            @RequestParam(defaultValue = "false") boolean isAdmin,
            Model model) {
        
        String[] courseIdArray  = courseId.split(",");
        int[] selectedCourseIds = Arrays.stream(courseIdArray)
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
        // Create User object and save to database using HandleUserService or UserRepository
        try {
            handleUserService.addNewUser(username, password, isAdmin, selectedCourseIds);
        } catch (DuplicateKeyException dke) {
            model.addAttribute("errorMessage", "Username already exists. Please choose a different username.");
            //TODO Handle the errorMessage in handleUserView so the user see the error.
        }
        List<User> users = handleUserService.getAllUsers();
        model.addAttribute("userList", users);

       
        return "redirect:/users/showusers"; 
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
     

        handleUserService.removeUserAndAssociatedBookings(selectedUserIds);
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
        List<User> users = handleUserService.getAllStudentUsers();
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

        List<BookingDTO> userBookings = handleUserService.getUsersActiveBookings(userId);
        List<BookingDTO> bookingList = handleUserService.getAvailableTimeSlots(userId);

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

        handleUserService.deleteUserBooking(userId, timeSlotId);

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

        handleUserService.assignTimeSlotToUser(userId, timeSlotId);

        model.addAttribute("username", userName);
        model.addAttribute("userId", userId);

        return "redirect:/users/showuserbooking";
    }
    
    @GetMapping("/users/editUserView")
    public String editUser(@RequestParam("userId") Integer userId, Model model){
        User user = handleUserService.getUserById(userId);
        model.addAttribute("user", user);
        
        List<CourseDTO> thisUsersCurrentCourses = user.getCourseIds();
        model.addAttribute("usersCourseList", thisUsersCurrentCourses);
        
        List<CourseDTO> courseList = handleUserService.fetchAvailableCourses();
        model.addAttribute("courseList", courseList);
        return "editUserView";
    }
    
    @PostMapping("/users/editUser")
    public String editUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String courseId,
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "false") boolean isAdmin,
            Model model){
        
        String[] courseIdArray  = courseId.split(",");
        int[] selectedCourseIds = Arrays.stream(courseIdArray)
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
        
        handleUserService.updateCurrentUser(userId, username, password, isAdmin, selectedCourseIds);
        return "redirect:/users/showusers";
    }    
}
