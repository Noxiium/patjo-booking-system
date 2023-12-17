package controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import model.BookingDTO;
import model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.BookingService;

/**
 *
 * BookingController class handles requests related to booking operations.
 *
 */
@Controller
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Handles GET request for displaying available courses. Retrieves a list
     * with available courses from the BookingService class, and adds it to the
     * model for rendering in view.
     *
     * @param model
     * @return selectCourseView
     */
    @RequestMapping("/booking/showcourses")
    public String showAvailableCourses(Model model) {
        List<CourseDTO> courseList = bookingService.fetchAvailableCourses();
        model.addAttribute("courseList", courseList);

        return "selectCourseView";
    }

    /**
     *
     * Handles GET request for displaying available time slots for the selected
     * course. Retrieves a booking list with time slots from the BookingService
     * class, and adds it to the model for rendering in view.
     *
     * @param selectedCourseId The id of the selected course
     * @param model
     * @return timeSlotsView
     */
    @RequestMapping("/booking/showtimeslots")
    public String showBookingListForCourse(@RequestParam("selectedCourse") int selectedCourseId, Model model) {
        System.out.println("selected course id: " + selectedCourseId);
        List<BookingDTO> bookingList = bookingService.fetchBookingListForCourse(selectedCourseId);

        model.addAttribute("bookingList", bookingList);
        return "timeSlotsView";
    }

    /**
     * Handles the request to book an available time slot, saves the booking information in
     * the database, and returns the view for confirming the booked time slot.
     *
     * @param selectedTimeSlot The selected time slot to be booked.
     * @param session The HttpSession containing user information.
     * @return The view name for confirming the booked time slot.
     */
    @RequestMapping("/booking/book-time-slot")
    public String confirmBookedTime(@RequestParam("selectedTimeSlot") String selectedTimeSlot, HttpSession session) {

        bookingService.saveBookedTime(selectedTimeSlot, session);

        System.out.println("SelectedTimeSlot = " + selectedTimeSlot);

        return "confirmedTimeSlotView";
    }
}
