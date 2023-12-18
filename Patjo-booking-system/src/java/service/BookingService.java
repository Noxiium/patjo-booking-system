package service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.BookingDTO;
import model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<CourseDTO> fetchAvailableCourses() {
        List<CourseDTO> courseList = bookingRepository.fetchAvailableCoursesFromDB();

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
    public void saveBookedTime(String selectedTimeSlot, HttpSession session) {

        // Retrieve the userId from the current session
        Integer userId = (Integer) session.getAttribute("userId");

        // Convert the selected time slot string to an Integer
        Integer selectedTimeSlotId = Integer.valueOf(selectedTimeSlot);
        bookingRepository.insertBookedTimeIntoDB(selectedTimeSlotId, userId);
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
}
