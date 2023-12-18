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

    public List<CourseDTO> fetchAvailableCoursesFromDB() {
        String query = "SELECT COURSE_ID,COURSE_NAME FROM BOOKING.COURSE";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CourseDTO.class));
    }

    public List<BookingDTO> fetchBookingListForCourseFromDB(int courseId) {
        String query = """
                       SELECT B.*
                       FROM BOOKING.BOOKING B
                       WHERE B.BOOKING_ID IN (
                           SELECT BL.BOOKING_ID
                           FROM BOOKING.LIST L
                           JOIN BOOKING.BOOKING_LIST BL ON L.LIST_ID = BL.LIST_ID
                           WHERE L.course_id =""" + courseId + ")";

        return jdbcTemplate.query(query, (rs, rowNum)
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
        String query = "INSERT INTO BOOKING.USERS_BOOKING(USERS_ID,BOOKING_ID)VALUES(?, ?)";
        jdbcTemplate.update(query, userId, selectedTimeSlotId);

        updateAvailability(selectedTimeSlotId);
    }

    /**
     * Update selected time slot column in DB , set AVAILABLE to FALSE.
     *
     * @param selectedTimeSlotId The ID of the selected time slot.
     */
    private void updateAvailability(Integer selectedTimeSlotId) {
        String query = "UPDATE BOOKING.BOOKING SET AVAILABLE = FALSE WHERE BOOKING_ID = ?";
        jdbcTemplate.update(query, selectedTimeSlotId);
    }

    /**
     * Fetches the booked time slots for the current user from the database.
     *
     * @param userId, The ID for the current user
     * @return A list of BookingDTO objects representing the user's bookings.
     */
    public List<BookingDTO> fetchUserBookingsFromDB(Integer userId) {
        String query = "SELECT B.* FROM BOOKING.USERS_BOOKING UB JOIN BOOKING.BOOKING B ON UB.BOOKING_ID = B.BOOKING_ID WHERE UB.USERS_ID = ?";

        return jdbcTemplate.query(query, (rs, rowNum)
                -> new BookingDTO(
                        rs.getInt("BOOKING_ID"),
                        rs.getString("BOOKING_TYPE_OF_SESSION"),
                        rs.getString("BOOKING_LOCATION"),
                        rs.getString("START_TIME"),
                        rs.getBoolean("AVAILABLE")
                ), userId);
    }
}
