package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.BookingDTO;
import model.CourseDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PresentationListRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PresentationListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    /**
     * Fetches all presentation lists from the database.
     *
     * @return A list of PresentationListDTO representing all presentation
     * lists.
     */
    public List<PresentationListDTO> fetchAllPresentationListsFromDB() {
        String query = "SELECT L.LIST_ID, U.USERSNAME, C.COURSE_NAME "
                + "FROM PATJODB.LIST AS L "
                + "JOIN PATJODB.USERS AS U ON L.USERS_ID = U.USERS_ID "
                + "JOIN PATJODB.COURSE AS C ON L.COURSE_ID = C.COURSE_ID";

        return jdbcTemplate.query(query, (rs, rowNum)
                -> new PresentationListDTO(
                        rs.getInt("LIST_ID"),
                        rs.getString("COURSE_NAME"),
                        rs.getString("USERSNAME")
                )
        );
    }

    /**
     * Fetches bookings for a selected presentation list from the database.
     *
     * @param selectedListId The ID of the selected presentation list.
     * @return A list of BookingDTO representing bookings for the selected
     * presentation list.
     */
    public List<BookingDTO> fetchSelectedPresentationListFromDB(int selectedListId) {
        String query = "SELECT * FROM PATJODB.BOOKING JOIN PATJODB.BOOKING_LIST ON PATJODB.BOOKING.BOOKING_ID = PATJODB.BOOKING_LIST.BOOKING_ID WHERE PATJODB.BOOKING_LIST.LIST_ID = ?";

        return jdbcTemplate.query(query, new Object[]{selectedListId}, (rs, rowNum)
                -> new BookingDTO(
                        rs.getInt("BOOKING_ID"),
                        rs.getString("BOOKING_TYPE_OF_SESSION"),
                        rs.getString("BOOKING_LOCATION"),
                        rs.getString("START_TIME"),
                        rs.getBoolean("AVAILABLE")
                )
        );
    }

    /**
     * Deletes bookings associated with the presentation list.
     *
     * @param selectedListId The ID of the presentation list
     */
    public void deleteAssociatedBookings(int selectedListId) {
        String query = "DELETE FROM PATJODB.BOOKING "
                + "WHERE booking_id IN ("
                + "  SELECT booking_id FROM PATJODB.BOOKING_LIST WHERE list_id = ?"
                + ")";
        jdbcTemplate.update(query, selectedListId);
    }

    /**
     * Deletes a presentation list from the database.
     *
     * @param selectedListId The ID of the presentation list to be deleted
     */
    public void deletePresentationListFromDB(int selectedListId) {
        String query = "DELETE FROM PATJODB.LIST WHERE LIST_ID = ?";
        jdbcTemplate.update(query, selectedListId);
    }

    public List<CourseDTO> fetchAvailableCoursesFromDB() {
        String query = "SELECT COURSE_ID,COURSE_NAME FROM PATJODB.COURSE";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CourseDTO.class));

    }

    /**
     * Saves a new list in the database and returns the generated list ID.
     *
     * @param userId The ID of the creator of the list
     * @param courseId The ID of the course associated with the list.
     * @return The generated list ID.
     */
    public int saveListInDBReturnListId(Integer userId, Integer courseId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PATJODB.LIST")
                .usingGeneratedKeyColumns("LIST_ID")
                .usingColumns("USERS_ID", "COURSE_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USERS_ID", userId);
        parameters.put("COURSE_ID", courseId);

        Number generatedId = -1;

        generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return generatedId.intValue();
    }

    /**
     * Saves a new booking in the database and returns the generated booking ID.
     *
     * @param bookingDTO The BookingDTO containing booking information.
     * @return The generated booking ID.
     */
    public int saveAssociatedBookingsInDB(BookingDTO bookingDTO) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PATJODB.BOOKING")
                .usingGeneratedKeyColumns("BOOKING_ID")
                .usingColumns("BOOKING_TYPE_OF_SESSION", "BOOKING_LOCATION", "START_TIME", "AVAILABLE");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("BOOKING_TYPE_OF_SESSION", bookingDTO.getTypeOfSession());
        parameters.put("BOOKING_LOCATION", bookingDTO.getLocation());
        parameters.put("START_TIME", bookingDTO.getStartTime());
        parameters.put("AVAILABLE", bookingDTO.getIsAvailable());

        Number generatedId = -1;

        generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return generatedId.intValue();
    }

    /**
     * Updates the reference table linking a list to bookings in the
     * database.
     *
     * @param listId The ID of the list to be associated with bookings.
     * @param bookingIds A List of booking IDs 
     */
    public void updateBookingListReferenceTable(int listId, List<Integer> bookingIds) {

        for (Integer id : bookingIds) {
            String query = "INSERT INTO PATJODB.BOOKING_LIST(LIST_ID,BOOKING_ID)VALUES (?,?)";
            jdbcTemplate.update(query, listId, id);
        }
    }
}
