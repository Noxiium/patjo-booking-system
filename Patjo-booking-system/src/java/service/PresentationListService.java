package service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
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
     * Deletes the selected presentation list along with its associated
     * bookings.
     *
     * @param selectedListId The ID of the presentation list to be deleted.
     */
    @Transactional
    public void deleteSelectedPresentationList(int selectedListId) {
        presentationListRepository.deleteAssociatedBookings(selectedListId);
        presentationListRepository.deletePresentationListFromDB(selectedListId);
    }

    /**
     * Retrieves a list of available courses for creating Presentation Lists.
     *
     * @return A List of CourseDTO representing the available courses.
     */
    public List<CourseDTO> fetchAvailableCourses() {
        List<CourseDTO> availableCourses = presentationListRepository.fetchAvailableCoursesFromDB();
        return availableCourses;
    }

    /**
     * Saves the created presentation list along with associated bookings in the
     * database.
     *
     * @param bookingDTOList List of BookingDTO objects representing the created
     * Presentation List.
     * @param session HttpSession object containing user-related information.
     * @param courseId Integer representing the ID of the course for which the
     * presentation list is created.
     */
    public void saveCreatedPresentationList(List<BookingDTO> bookingDTOList, HttpSession session, Integer courseId) {
        Integer userId = (Integer) session.getAttribute("userId");

        int listId = presentationListRepository.saveListInDBReturnListId(userId, courseId);
        List<Integer> bookingIds = saveAssociatedBookings(bookingDTOList);
        presentationListRepository.updateBookingListReferenceTable(listId, bookingIds);
    }

    /**
     * Saves a list of associated bookings in the database and returns a list of
     * generated booking IDs.
     *
     * @param bookingDTOList List of BookingDTO objects representing bookings to
     * be saved.
     * @return List of Integer representing the generated booking IDs.
     */
    private List<Integer> saveAssociatedBookings(List<BookingDTO> bookingDTOList) {
        List<Integer> bookingIds = new ArrayList<>();

        for (BookingDTO booking : bookingDTOList) {
            int id = presentationListRepository.saveAssociatedBookingsInDB(booking);
            bookingIds.add(id);
        }
        return bookingIds;

    }
}
