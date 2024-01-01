package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.BookingDTO;
import model.CourseDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.PresentationListService;

@Controller
public class PresentationListController {

    private final PresentationListService presentationListService;

    @Autowired
    public PresentationListController(PresentationListService presentationListService) {
        this.presentationListService = presentationListService;
    }

    /**
     * Handles the request to display all active presentation lists.
     *
     * @param model The model to add attributes for the view.
     * @return
     */
    @RequestMapping("/presentationlist")
    public String showAllActivePresentationLists(Model model) {

        List<PresentationListDTO> activePresentationLists = presentationListService.fetchAllPresentationLists();
        model.addAttribute("activePresentationLists", activePresentationLists);
        return "presentationListView";
    }

    /**
     * Handles the request to display a specific presentation list.
     *
     * @param selectedListId The ID of the selected presentation list.
     * @param model The model to add attributes for the view.
     * @return
     */
    @RequestMapping("/showpresentationlist")
    public String showPresentationList(@RequestParam("selectedListId") int selectedListId, Model model) {
        List<BookingDTO> selectedPresentationList = presentationListService.fetchSelectedPresentationList(selectedListId);
        System.out.println("Selected List ID: " + selectedListId);
        model.addAttribute("selectedPresentationList", selectedPresentationList);
        model.addAttribute("listId", selectedListId);
        return "presentationListView";
    }

    /**
     * Handles the request to delete a selected presentation list.
     *
     * @param selectedListId The ID of the presentation list to be deleted.
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/deleteselectedlist")
    public String deletePresentationList(@RequestParam("selectedList") int selectedListId, RedirectAttributes redirectAttributes) {
        presentationListService.deleteSelectedPresentationList(selectedListId);
        redirectAttributes.addFlashAttribute("deletedlist", "deletedlist");
        return "redirect:/presentationlist";
    }

    @RequestMapping("/presentationlist/create")
    public String showCreatePresentationListView(Model model) {
        List<CourseDTO> availableCourses = presentationListService.fetchAvailableCourses();
        model.addAttribute("courseList", availableCourses);
        return "createPresentationListView";
    }

    /**
     * Handles the HTTP POST request to save a created presentation list.
     *
     * @param typeOfSession List of strings representing the types of sessions
     * in the presentation list.
     * @param location List of strings representing the locations associated
     * with each session.
     * @param startTime List of strings representing the start times for each
     * session.
     * @param courseId String representing the ID of the course for which the
     * presentation list is created.
     * @param session HttpSession object containing user-related information.
     * @return
     */
    @RequestMapping("/presentationlist/savepresentationlist")
    public String savePresentationList(@RequestParam("typeOfSession") List<String> typeOfSession,
            @RequestParam("location") List<String> location,
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") List<Date> startTime,
            @RequestParam("courseId") String courseId,
            HttpSession session) {

        presentationListService.saveCreatedPresentationList(typeOfSession, location, startTime, session, Integer.valueOf(courseId));
        return "redirect:/adminmain";
    }

    /**
     * Handles the generation and saving of a presentation list.
     *
     * @param typeOfSession The type of session 
     * @param location The location 
     * @param startTime The starting time for the first time slot
     * @param numberOfTimeSlots The number of time slots to create
     * @param intervalBetweenTimeSlots The interval between time slots in
     * minutes.
     * @param courseId The ID of the course
     * @param session The HttpSession containing user-related information.
     * @return 
     */
    @RequestMapping("/presentationlist/generatepresentationlist")
    public String saveGeneratedPresentationList(@RequestParam("typeOfSession") String typeOfSession,
            @RequestParam("location") String location,
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startTime,
            @RequestParam("numberOfTimeSlots") int numberOfTimeSlots,
            @RequestParam("intervalBetweenTimeSlots") int intervalBetweenTimeSlots,
            @RequestParam("course") String courseId,
            HttpSession session) {

        presentationListService.saveGeneratedPresentationList(typeOfSession, location, startTime, numberOfTimeSlots, intervalBetweenTimeSlots, courseId, session);
        return "redirect:/adminmain";
    }

}
