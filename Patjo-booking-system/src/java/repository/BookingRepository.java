package repository;

import java.util.List;
import model.BookingDTO;
import model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * BookingRepository class manages interactions with the database for
 * booking-related transactions.
 *
 */
@Repository
public class BookingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of available courses for a given user from the database.
     *
     * @param userId The ID of the user
     * @return A list of CourseDTO representing available courses.
     */
    public List<CourseDTO> fetchAvailableCoursesFromDB(Integer userId) {
        String query = "SELECT C.COURSE_ID, C.COURSE_NAME FROM PATJODB.COURSE C JOIN PATJODB.USERS_COURSE UC ON C.COURSE_ID = UC.COURSE_ID WHERE UC.USERS_ID = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CourseDTO.class), userId);
    }

    /**
     * Retrieves a list of bookings for a specific course from the database.
     *
     * @param courseId The ID of the course 
     * @return A list of BookingDTO representing bookings for the
     * specified course.
     */
    public List<BookingDTO> fetchBookingListForCourseFromDB(int courseId) {
        String query = """
                   SELECT B.*
                   FROM PATJODB.BOOKING B
                   WHERE B.BOOKING_ID IN (
                       SELECT BL.BOOKING_ID
                       FROM PATJODB.LIST L
                       JOIN PATJODB.BOOKING_LIST BL ON L.LIST_ID = BL.LIST_ID
                       WHERE L.course_id = ?
                   )""";

        return jdbcTemplate.query(query, new Object[]{courseId}, (rs, rowNum)
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
     * Inserts a booked time slot into the USERS_BOOKING table.
     *
     * @param selectedTimeSlotId The ID of the selected time slot.
     * @param userId The ID of the user making the booking.
     */
    public void insertBookedTimeIntoDB(Integer selectedTimeSlotId, Integer userId) {
        String query = "INSERT INTO PATJODB.USERS_BOOKING(USERS_ID,BOOKING_ID)VALUES(?, ?)";
        jdbcTemplate.update(query, userId, selectedTimeSlotId);

        updateAvailability(selectedTimeSlotId);
    }

    /**
     * Update selected time slot column in DB , set AVAILABLE to FALSE.
     *
     * @param selectedTimeSlotId The ID of the selected time slot.
     */
    private void updateAvailability(Integer selectedTimeSlotId) {
        String query = "UPDATE PATJODB.BOOKING SET AVAILABLE = FALSE WHERE BOOKING_ID = ?";
        jdbcTemplate.update(query, selectedTimeSlotId);
    }

    /**
     * Fetches the booked time slots for the current user from the database.
     *
     * @param userId, The ID for the current user
     * @return A list of BookingDTO objects representing the user's bookings.
     */
    public List<BookingDTO> fetchUserBookingsFromDB(Integer userId) {
        String query = "SELECT B.*, C.COURSE_NAME "
                + "FROM PATJODB.USERS_BOOKING UB "
                + "JOIN PATJODB.BOOKING B ON UB.BOOKING_ID = B.BOOKING_ID "
                + "JOIN PATJODB.BOOKING_LIST BL ON B.BOOKING_ID = BL.BOOKING_ID "
                + "JOIN PATJODB.LIST L ON BL.LIST_ID = L.LIST_ID "
                + "JOIN PATJODB.COURSE C ON L.COURSE_ID = C.COURSE_ID "
                + "WHERE UB.USERS_ID = ?";

        return jdbcTemplate.query(query, (rs, rowNum)
                -> new BookingDTO(
                        rs.getInt("BOOKING_ID"),
                        rs.getString("BOOKING_TYPE_OF_SESSION"),
                        rs.getString("BOOKING_LOCATION"),
                        rs.getString("START_TIME"),
                        rs.getBoolean("AVAILABLE"),
                        rs.getString("COURSE_NAME")
                ), userId);
    }

    /**
     * Removes a booked time slot from the USERS_BOOKING table for the specified
     * user. Marks the corresponding time slot as available in the BOOKING
     * table.
     *
     * @param selectedTimeSlotId The ID of the selected time slot to be removed.
     * @param userId The ID of the specified user
     */
    public void removeBookedTimeSlotFromDB(Integer selectedTimeSlotId, Integer userId) {
        String removeQuery = "DELETE FROM PATJODB.USERS_BOOKING WHERE USERS_ID = ? AND BOOKING_ID = ?";
        jdbcTemplate.update(removeQuery, userId, selectedTimeSlotId);

        String updateQuery = "UPDATE PATJODB.BOOKING SET AVAILABLE = TRUE WHERE BOOKING_ID = ?";
        jdbcTemplate.update(updateQuery, selectedTimeSlotId);
    }
}
