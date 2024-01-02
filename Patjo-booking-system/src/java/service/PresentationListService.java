package service;

import controller.WebSocketEndpoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // Send a message to all connected WebSocket clients to notify them of an update.
        WebSocketEndpoint.sendMessageToAll("updateBooking");
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
     * @param typeOfSession
     * @param location
     * @param startTime
     * @param session HttpSession object containing user-related information.
     * @param courseId Integer representing the ID of the course for which the
     * presentation list is created.
     * @return 
     */
   
    public Map<String, Object> saveCreatedPresentationList(List<String> typeOfSession, List<String> location, List<Date> startTime, HttpSession session, String courseId) {

        // Get the ID of the user creating the list
        Integer userId = (Integer) session.getAttribute("userId");

        // Build a list of BookingDTO objects 
        List<BookingDTO> bookingDTOList = buildBookingDTOList(typeOfSession, location, startTime);

        // Insert the presentation list and its creator into the database, returning the generated list ID
        int listId = presentationListRepository.saveListInDBReturnListId(userId, Integer.valueOf(courseId));

        // Save the associated bookings in the database, returning the generated booking IDs
        List<Integer> bookingIds = saveAssociatedBookings(bookingDTOList);

        // Update the cross-reference table between the presentation list and bookings
        presentationListRepository.updateBookingListReferenceTable(listId, bookingIds);
        
        //Get course name from course ID
        String courseName = presentationListRepository.getCourseNameFromId(courseId);

        // Send a message to all connected WebSocket clients to notify them of an update.
        WebSocketEndpoint.sendMessageToAll("updateBooking");
        
        
        Map<String, Object> result = new HashMap<>();
        result.put("listId",listId);
        result.put("bookingDTOList",bookingDTOList);
        result.put("courseName", courseName);
        
        return result;
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

    /**
     * Saves a generated presentation list in the database
     *
     * @param typeOfSession The type of session
     * @param location The location
     * @param startTime The starting time for the first time slot
     * @param numberOfTimeSlots The total number of time slots in the
     * presentation list.
     * @param intervalBetweenTimeSlots The time interval between each time slot.
     * @param courseId The ID of the course
     * @param session The HttpSession containing information about the current
     * user.
     * @return 
     */
    public Map<String, Object> saveGeneratedPresentationList(String typeOfSession, String location,
            Date startTime, int numberOfTimeSlots, int intervalBetweenTimeSlots,
            String courseId, HttpSession session) {

        // Get the ID of the user creating the list
        Integer userId = (Integer) session.getAttribute("userId");

        // Insert the presentation list and its creator into the database, returning the generated list ID
        int listId = presentationListRepository.saveListInDBReturnListId(userId, Integer.valueOf(courseId));

        // Calculate time intervals for all time slots in the presentation list
        List<String> timeSlotIntervals = calculateTimeSlotIntervals(startTime, intervalBetweenTimeSlots, numberOfTimeSlots);

        // Build a list of BookingDTO objects 
        List<BookingDTO> bookingDTOList = buildBookingDTOListForGeneratedSlots(typeOfSession, location, timeSlotIntervals);

        // Save the associated bookings in the database, returning the generated booking IDs
        List<Integer> bookingIds = saveAssociatedBookings(bookingDTOList);

        // Update the cross-reference table between the presentation list and bookings
        presentationListRepository.updateBookingListReferenceTable(listId, bookingIds);

        // Send a message to all connected WebSocket clients to notify them of an update.
        WebSocketEndpoint.sendMessageToAll("updateBooking");
        
        String courseName = presentationListRepository.getCourseNameFromId(courseId);
        Map<String, Object> result = new HashMap<>();
        System.out.println("coursename" + courseName);
        result.put("listId",listId);
        result.put("bookingDTOList",bookingDTOList);
        result.put("courseName", courseName);
        
        return result;
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
    private List<BookingDTO> buildBookingDTOList(List<String> typeOfSession, List<String> location, List<Date> startTime) {

        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (int i = 0; i < typeOfSession.size(); i++) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setTypeOfSession(typeOfSession.get(i));
            bookingDTO.setLocation(location.get(i));
            bookingDTO.setStartTime(formatDate(startTime.get(i)));
            bookingDTO.setIsAvailable(true);

            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }

    /**
     * Creates time slot intervals based on the provided start time, interval
     * between time slots, and the number of time slots.
     *
     * @param startTime The starting time for the first time slots.
     * @param intervalBetweenTimeSlots The interval between time slots in
     * minutes.
     * @param numberOfTimeSlots The number of time slots to generate.
     * @return A list of formatted time slot intervals.
     */
    private List<String> calculateTimeSlotIntervals(Date startTime, int intervalBetweenTimeSlots, int numberOfTimeSlots) {
        List<String> timeSlots = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        for (int i = 0; i < numberOfTimeSlots; i++) {
            timeSlots.add(formatDate(calendar.getTime()));
            calendar.add(Calendar.MINUTE, intervalBetweenTimeSlots);
        }
        return timeSlots;
    }

    /**
     * Formats the given date into a string with the pattern "yyyy-MM-dd HH:mm".
     *
     * @param date The date to be formatted.
     * @return A formatted string representation of the date.
     */
    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * Builds a list of BookingDTO objects for the provided time slot intervals,
     * type of session, and location.
     *
     * @param typeOfSession The type of session for the bookings.
     * @param location The location for the bookings.
     * @param timeSlotIntervals The list of formatted time slot intervals.
     * @return A list of BookingDTO objects
     */
    private List<BookingDTO> buildBookingDTOListForGeneratedSlots(String typeOfSession, String location, List<String> timeSlotIntervals) {

        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (int i = 0; i < timeSlotIntervals.size(); i++) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setTypeOfSession(typeOfSession);
            bookingDTO.setLocation(location);
            bookingDTO.setStartTime(timeSlotIntervals.get(i));
            bookingDTO.setIsAvailable(true);

            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }
}
