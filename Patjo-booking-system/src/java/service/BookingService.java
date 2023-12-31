package service;

import controller.WebSocketEndpoint;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.BookingDTO;
import model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BookingRepository;

/**
 * The BookingService class manages the business logic for handling
 * booking-related operations.
 *
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieves the list of available courses and returns it.
     *
     * @return List of available courses
     */
    public List<CourseDTO> fetchAvailableCourses(HttpSession session) {
        
        // Retrieve the userId from the current session
        Integer userId = (Integer) session.getAttribute("userId");
        
        List<CourseDTO> courseList = bookingRepository.fetchAvailableCoursesFromDB(userId);

        for (CourseDTO course : courseList) {
            System.out.println(course.getCourseName());
            System.out.println(course.getCourseId());

        }
        return courseList;
    }

    /**
     * Retrieves the list of time slots for the selected course and returns it
     *
     * @param courseId The id of the selected course
     * @return bookingList, the list containing time slots (BookingDTO)
     */
    public List<BookingDTO> fetchBookingListForCourse(int courseId) {
        List<BookingDTO> bookingList = bookingRepository.fetchBookingListForCourseFromDB(courseId);

        return bookingList;
    }

    /**
     * Saves a booked time slot for the user in the database.
     *
     * @param selectedTimeSlot The selected time slot to be booked.
     * @param session The HttpSession containing user information.
     */
    @Transactional
    public void saveBookedTime(String selectedTimeSlot, HttpSession session) {

        // Retrieve the userId from the current session
        Integer userId = (Integer) session.getAttribute("userId");

        // Convert the selected time slot string to an Integer
        Integer selectedTimeSlotId = Integer.valueOf(selectedTimeSlot);
        bookingRepository.insertBookedTimeIntoDB(selectedTimeSlotId, userId);
        
        // Send a message to all connected WebSocket clients to notify them of an update.
         WebSocketEndpoint.sendMessageToAll("updateBooking");
    }

    /**
     * Fetches the booked time slots for the current user..
     *
     * @param session The HttpSession containing user information.
     * @return A list of BookingDTO objects representing the user's bookings.
     */
    public List<BookingDTO> fetchUserBookings(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<BookingDTO> userBookings = bookingRepository.fetchUserBookingsFromDB(userId);
        return userBookings;
    }

    /**
     * Removes a booked time slot for the current user.
     *
     * @param selectedTimeSlot The ID of the selected time slot to be removed.
     * @param session The HttpSession containing user information.
     */
    @Transactional
    public void removeBookedTimeSlot(String selectedTimeSlot, HttpSession session) {
        Integer selectedTimeSlotId = Integer.valueOf(selectedTimeSlot);
        Integer userId = (Integer) session.getAttribute("userId");

        bookingRepository.removeBookedTimeSlotFromDB(selectedTimeSlotId, userId);
        
        // Send a message to all connected WebSocket clients to notify them of an update.
         WebSocketEndpoint.sendMessageToAll("updateBooking");
    }

}
