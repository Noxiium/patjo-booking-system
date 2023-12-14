package repository;

import java.util.ArrayList;
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
                        rs.getString("START_TIME")
                )
        );
    }

}
