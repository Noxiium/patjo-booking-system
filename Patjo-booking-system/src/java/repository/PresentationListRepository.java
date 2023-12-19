package repository;

import java.util.List;
import model.BookingDTO;
import model.PresentationListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

}
