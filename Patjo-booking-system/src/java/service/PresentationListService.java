package service;

import java.util.List;
import model.BookingDTO;
import model.CourseDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PresentationListRepository;

/**
 * Service class responsible for handling business logic related to presentation
 * lists. This class interacts with the underlying repository to fetch and
 * process data.
 */
@Service
public class PresentationListService {

    private final PresentationListRepository presentationListRepository;

    @Autowired
    public PresentationListService(PresentationListRepository presentationListRepository) {
        this.presentationListRepository = presentationListRepository;
    }

    /**
     * Fetches all presentation lists from the database.
     *
     * @return A list of PresentationListDTO containing details of all
     * presentation lists.
     */
    public List<PresentationListDTO> fetchAllPresentationLists() {
        return presentationListRepository.fetchAllPresentationListsFromDB();
    }

    /**
     * Fetches the selected presentation list based on the provided list ID.
     *
     * @param selectedListId The ID of the selected presentation list.
     * @return A list of BookingDTO containing details of bookings for the
     * selected presentation list.
     */
    public List<BookingDTO> fetchSelectedPresentationList(int selectedListId) {
        return presentationListRepository.fetchSelectedPresentationListFromDB(selectedListId);
    }

    /**
     * Deletes the selected presentation list along with its associated bookings.
     *
     * @param selectedListId The ID of the presentation list to be deleted.
     */
    @Transactional
    public void deleteSelectedPresentationList(int selectedListId) {
       presentationListRepository.deleteAssociatedBookings(selectedListId);
       presentationListRepository.deletePresentationListFromDB(selectedListId);
    }

    public List<CourseDTO> fetchAvailableCourses() {
      List<CourseDTO> availableCourses = presentationListRepository.fetchAvailableCoursesFromDB();
      return availableCourses;
    }

}
