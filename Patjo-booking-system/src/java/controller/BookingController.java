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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @RequestMapping("/showcourses")
    public String showAvailableCourses(Model model, HttpSession session) {
        List<CourseDTO> courseList = bookingService.fetchAvailableCourses(session);
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
    @RequestMapping("/showtimeslots")
    public String showBookingListForCourse(@RequestParam("selectedCourse") int selectedCourseId, Model model) {
        System.out.println("selected course id: " + selectedCourseId);
        List<BookingDTO> bookingList = bookingService.fetchBookingListForCourse(selectedCourseId);

        model.addAttribute("bookingList", bookingList);
        return "timeSlotsView";
    }

    /**
     * Handles the request to book an available time slot, saves the booking
     * information in the database, and returns the main view for confirming the
     * booked time slot.
     *
     * @param selectedTimeSlot The selected time slot to be booked.
     * @param session The HttpSession containing user information.
     * @param redirectAttributes Used to add flash attributes for the next
     * redirect.
     * @return The view name for confirming the booked time slot.
     */
    @RequestMapping("/booktimeslot")
    public String confirmBookedTime(@RequestParam("selectedTimeSlot") String selectedTimeSlot,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        bookingService.saveBookedTime(selectedTimeSlot, session);

        // Add a flash attribute to indicate successful booking confirmation
        redirectAttributes.addFlashAttribute("confirmation", "confirmation");

        return "redirect:/main";

    }

    /**
     * Handles the request to show the booked time slots for the current user,
     * fetches the user's bookings from the database, and returns the view
     * displaying the booked time slots.
     *
     * @param session The HttpSession containing user information.
     * @param model The model to add attributes for the view.
     * @return The view name for displaying the booked time slots.
     */
    @RequestMapping("/mybookings")
    public String showUserBookings(HttpSession session, Model model) {
        List<BookingDTO> userBookings = bookingService.fetchUserBookings(session);
        model.addAttribute("userBookings", userBookings);

        return "bookedTimeSlotsView";
    }

    /**
     * Handles the request to remove a booked time slot, delegates the operation
     * to the booking service, and returns the main view for confirming the
     * deleted time slot.
     *
     * @param selectedTimeSlot The ID of the selected time slot to be removed.
     * @param session The HttpSession containing user information.
     * @param redirectAttributes
     * @return The view name for displaying the booked time slots.
     */
    @RequestMapping("/removetimeslot")
    public String removeBookedTimeSlot(@RequestParam("selectedTimeSlot") String selectedTimeSlot, HttpSession session, RedirectAttributes redirectAttributes) {

        bookingService.removeBookedTimeSlot(selectedTimeSlot, session);
        
       // Add a flash attribute to indicate successful booking confirmation
        redirectAttributes.addFlashAttribute("deletedtimeslot", "deletedtimeslot");
         return "redirect:/main";
    }
}
