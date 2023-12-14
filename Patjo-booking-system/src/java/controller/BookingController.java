package controller;

import java.util.List;
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

    @RequestMapping("/booking/showtimeslots")
    public String showTimeSlotLists(@RequestParam("selectedCourse") int selectedCourseId, Model model) {
        System.out.println("selected course id: " + selectedCourseId);
        
        return "timeSlotsView"; 
    }
}
