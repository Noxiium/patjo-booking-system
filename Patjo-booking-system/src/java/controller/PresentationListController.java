package controller;

import java.util.List;
import model.BookingDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}
