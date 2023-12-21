package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.BookingDTO;
import model.CourseDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String showPresentationList(@RequestParam("selectedList") int selectedListId, Model model) {
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
            @RequestParam("startTime") List<String> startTime,
            @RequestParam("courseId") String courseId,
            HttpSession session) {

        System.out.println("Selected Course Id:" + courseId);
        List<BookingDTO> bookingDTOList = buildBookingDTOList(typeOfSession, location, startTime);
        presentationListService.saveCreatedPresentationList(bookingDTOList, session, Integer.valueOf(courseId));
        return "redirect:/adminmain";
    }

    /**
     * Builds a list of BookingDTO objects based on input lists for
     * typeOfSession, location, and startTime.
     *
     * @param typeOfSession List of strings representing the types of sessions.
     * @param location List of strings representing the locations associated
     * with each session.
     * @param startTime List of strings representing the start times for each
     * session.
     * @return A List of BookingDTO objects representing the booking information
     * for each session.
     */
    private List<BookingDTO> buildBookingDTOList(List<String> typeOfSession, List<String> location, List<String> startTime) {

        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (int i = 0; i < typeOfSession.size(); i++) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setTypeOfSession(typeOfSession.get(i));
            bookingDTO.setLocation(location.get(i));
            bookingDTO.setStartTime(startTime.get(i));
            bookingDTO.setIsAvailable(true);

            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }
}
