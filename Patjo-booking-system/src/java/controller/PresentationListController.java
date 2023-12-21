package controller;

import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping("/presentationlist/savebooking")
    public String saveBooking(@RequestParam("typeOfSession") List<String> typeOfSession,
            @RequestParam("location") List<String> location,
            @RequestParam("startTime") List<String> startTime,
            @RequestParam("courseId") String courseId) {

        System.out.println("Selected Course Id:" + courseId);
        List<BookingDTO> bookingDTOList = buildBookingDTOList(typeOfSession, location, startTime);

        return "redirect:/adminmain";
    }

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
        
        
        for (BookingDTO bookingDTO : bookingDTOList) {
            System.out.println("Type of Session: " + bookingDTO.getTypeOfSession());
            System.out.println("Location: " + bookingDTO.getLocation());
            System.out.println("Start Time: " + bookingDTO.getStartTime());
            System.out.println("Is Available: " + bookingDTO.getIsAvailable());
            System.out.println("--------------------------------");

        }
        return bookingDTOList;
    }
}
