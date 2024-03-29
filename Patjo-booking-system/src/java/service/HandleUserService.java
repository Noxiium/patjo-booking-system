package service;

import controller.WebSocketEndpoint;
import java.util.ArrayList;
import java.util.List;
import model.BookingDTO;
import model.CourseDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.HandleUserRepository;

@Service
public class HandleUserService {

    private final HandleUserRepository handleUserRepository;
    

    @Autowired
    public HandleUserService(HandleUserRepository handleUserRepository) {
        this.handleUserRepository = handleUserRepository;
       
    }

    @Transactional
    public void saveUser(User user) {
        handleUserRepository.saveUser(user);
        
    }
    
    public User getUserById(Integer userId) {
        return handleUserRepository.fetchUserFromDB(userId);
    }

    /**
     * Adds a new user to the database.
     *
     * @param  username    the username of the new user
     * @param  password    the password of the new user
     * @param  isAdmin     true if the new user is an admin, false otherwise
     * @param  courseIDs   an array of course IDs that the new user is enrolled in
     */
    public void addNewUser(String username, String password, Boolean isAdmin, int[] courseIDs) {
        int isAdminInt = 0;
        if (isAdmin) {
            isAdminInt = 1;
        }
        User newUser = new User(username, password, isAdminInt);
        saveUser(newUser);
        int userId = handleUserRepository.fetchUserID(newUser);
        for(int courseId : courseIDs)
            handleUserRepository.insertCourseAndUserIds(userId, courseId);
        WebSocketEndpoint.sendMessageToAll("updateBooking");
    }
    
    /**
     * Updates the selected user with the given information.
     *
     * @param  userId      the ID of the user
     * @param  username    the new username for the user
     * @param  password    the new password for the user
     * @param  isAdmin     a boolean indicating whether the user is an admin or not
     * @param  courseIDs   an array of course IDs that the user is enrolled in
     */
    public void updateSelectedUser(Integer userId, String username, String password, Boolean isAdmin, int[] courseIDs){
        int isAdminInt = 0;
        if (isAdmin) {
            isAdminInt = 1;
        }
        User newUser = new User(username, password, isAdminInt);
        newUser.setUserId(userId);
        
        handleUserRepository.updateUser(newUser);
        handleUserRepository.updateUserCourses(userId, courseIDs);
    
    }

    /**
     * Retrieves all users from the database.
     *
     * @return  a list of User objects representing all users
     */
    public List<User> getAllUsers() {
        return handleUserRepository.fetchAllUsersFromDB();
    }

    /**
     * Retrieves a list of all admin users from an existing list of users.
     *
     * @param  allUsers  the list of users to search through
     * @return           the list of admin users found
     */
    public List<User> getAllAdminUsersFromExistingList(List<User> allUsers) {
        List<User> adminUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getIsAdmin() == 1) {
                adminUsers.add(user);
            }
        }
        return adminUsers;
    }

    /**
     * Retrieves a list of non-admin users from the provided list of users.
     *
     * @param  allUsers  the list of users to filter
     * @return           a list containing only the non-admin users
     */
    public List<User> getAllNonAdminUsersFromExistingList(List<User> allUsers) {
        List<User> nonAdminUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getIsAdmin() == 0) {
                nonAdminUsers.add(user);
            }
        }
        return nonAdminUsers;
    }


    /**
     * Removes the selected user(s) and their associated bookings. If a user has
     * associated bookings, it calls the method
     * updateAssociatedBookingsAvailability to set the bookings to available.
     *
     * @param selectedUserIds Array of user IDs to be removed.
     */
    @Transactional
    public void removeUserAndAssociatedBookings(String[] selectedUserIds) {

        for (String id : selectedUserIds) {
            Integer userId = Integer.valueOf(id);
            List<Integer> bookingIds = handleUserRepository.checkAssociatedBookings(userId);

            if (!bookingIds.isEmpty()) {
                updateAssociatedBookingsAvailability(bookingIds);
            }
            handleUserRepository.removeUserFromDB(userId);
            
           // Send a message to all connected WebSocket clients to notify them of an update.
            WebSocketEndpoint.sendMessageToAll("updateBooking" + id);
        }
    }

    /**
     * Sets a given users associated bookings to available.
     *
     * @param bookingIds List of booking IDs associated with the user.
     */
    @Transactional
    private void updateAssociatedBookingsAvailability(List<Integer> bookingIds) {

        for (Integer bookingId : bookingIds) {
            handleUserRepository.markAssociatedBookingsAsAvailable(bookingId);
        }
    }

    /**
     * Retrieves all student users from the database.
     *
     * @return List of User objects representing all student users.
     */
    public List<User> getAllStudentUsers() {
        List<User> users = getAllUsers();
        return getAllNonAdminUsersFromExistingList(users);

    }

    /**
     * Retrieves active bookings for a specific user from the database.
     *
     * @param userId The ID of the user
     * @return List of BookingDTO representing active bookings for the user.
     */
    public List<BookingDTO> getUsersActiveBookings(Integer userId) {
        return handleUserRepository.fetchUserBookingsFromDB(userId);
    }

    /**
     * Retrieves available time slots for a specific user from the database.
     *
     * @param userId The ID of the user
     * @return List of BookingDTO representing available time slots for the
     * user.
     */
    public List<BookingDTO> getAvailableTimeSlots(Integer userId) {
        return handleUserRepository.getAvailableTimeSlotsFromDB(userId);

    }

    /**
     * Deletes a user's booking for a specific time slot and marks that time
     * slot as available.
     *
     * @param userId The ID of the user
     * @param timeSlotId The ID of the time slot to be deleted.
     */
    public void deleteUserBooking(Integer userId, Integer timeSlotId) {
        System.out.println("userservice");
        handleUserRepository.deleteUserBookingFromDB(userId, timeSlotId);
        handleUserRepository.markAssociatedBookingsAsAvailable(timeSlotId);
        
        // Send a message to all connected WebSocket clients to notify them of an update.
        WebSocketEndpoint.sendMessageToAll("updateBooking");
    }

    /**
     * Assigns a time slot to a user and marks the time slot as non-available.
     *
     * @param userId The ID of the user
     * @param timeSlotId The ID of the time slot to be assigned.
     */
    public void assignTimeSlotToUser(Integer userId, Integer timeSlotId) {
        handleUserRepository.assignTimeSlotToUser(userId, timeSlotId);
        handleUserRepository.markTimeSlotAsNonAvailable(timeSlotId);
        
        // Send a message to all connected WebSocket clients to notify them of an update.
         WebSocketEndpoint.sendMessageToAll("updateBooking");
    }
    
     /**
      * Fetches the available courses.
      *
      * @return  the list of available courses
      */
     public List<CourseDTO> fetchAvailableCourses() {
        List<CourseDTO> availableCourses = handleUserRepository.fetchAvailableCoursesFromDB();
        return availableCourses;
    }
}
