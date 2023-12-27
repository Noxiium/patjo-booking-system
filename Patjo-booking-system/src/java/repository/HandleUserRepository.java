package repository;

import java.util.List;
import model.BookingDTO;
import model.PresentationListDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HandleUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HandleUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO PATJODB.USERS (USERSNAME, PASSWORD, ADMIN) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getIsAdmin());
    }

    public Integer fetchUserID(User user) {
        String query = "SELECT ID FROM PATJODB.USERS WHERE USERSNAME = ? AND PASSWORD = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, user.getUsername(), user.getPassword());
    }

    public List<User> fetchAllUsersFromDB() {
        String sql = "SELECT * FROM PATJODB.USERS";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setUserId(rs.getInt("users_id"));
                user.setUsername(rs.getString("usersname"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getInt("admin"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //TODO method, check if password match
    /**
     * Retrieves a list of booking IDs associated with a given user.
     *
     * @param userId ID of the user.
     * @return List of booking IDs associated with the user.
     */
    public List<Integer> checkAssociatedBookings(Integer userId) {
        String query = "SELECT BOOKING_ID FROM PATJODB.USERS_BOOKING WHERE USERS_ID = ?";
        return jdbcTemplate.queryForList(query, Integer.class, userId);
    }

    /**
     * Marks the associated booking as available in the database.
     *
     * @param bookingId ID of the booking to be marked as available.
     */
    public void markAssociatedBookingsAsAvailable(Integer bookingId) {
        String query = "UPDATE PATJODB.BOOKING SET AVAILABLE = TRUE WHERE BOOKING_ID = ?";
        jdbcTemplate.update(query, bookingId);
    }

    /**
     * Removes a user from the database.
     *
     * @param userId ID of the user to be removed.
     */
    public void removeUserFromDB(Integer userId) {
        String query = "DELETE FROM PATJODB.USERS WHERE USERS_ID = ?";
        jdbcTemplate.update(query, userId);
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
                + "JOIN PATJODB.BOOKING_LIST BL ON UB.BOOKING_ID = BL.BOOKING_ID "
                + "JOIN PATJODB.BOOKING B ON BL.BOOKING_ID = B.BOOKING_ID "
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
     * Retrieves available time slots for a user based on their enrolled
     * courses.
     *
     * @param userId The ID of the user for whom available time slots are
     * retrieved.
     * @return List of BookingDTO representing available time slots
     */
    public List<BookingDTO> getAvailableTimeSlotsFromDB(Integer userId) {
        String query = "SELECT B.*, C.COURSE_NAME "
                + "FROM PATJODB.USERS_COURSE UC "
                + "JOIN PATJODB.LIST L ON UC.COURSE_ID = L.COURSE_ID "
                + "JOIN PATJODB.BOOKING_LIST BL ON L.LIST_ID = BL.LIST_ID "
                + "JOIN PATJODB.BOOKING B ON BL.BOOKING_ID = B.BOOKING_ID "
                + "JOIN PATJODB.COURSE C ON L.COURSE_ID = C.COURSE_ID "
                + "WHERE UC.USERS_ID = ?";

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
     * Deletes a user's booking for a specific time slot.
     *
     * @param userId The ID of the user whose booking is to be deleted.
     * @param timeSlotId The ID of the time slot to be deleted.
     */
    public void deleteUserBookingFromDB(Integer userId, Integer timeSlotId) {

        String query = "DELETE FROM PATJODB.USERS_BOOKING WHERE USERS_ID = ? AND BOOKING_ID = ?";
        jdbcTemplate.update(query, userId, timeSlotId);
    }

    /**
     * Assigns a time slot to a user
     *
     * @param userId The ID of the user to whom the time slot is assigned.
     * @param timeSlotId The ID of the time slot to be assigned.
     */
    public void assignTimeSlotToUser(Integer userId, Integer timeSlotId) {
        String query = "INSERT INTO PATJODB.USERS_BOOKING(USERS_ID,BOOKING_ID)VALUES(?, ?)";
        jdbcTemplate.update(query, userId, timeSlotId);
    }

    /**
     * Marks a time slot as non-available
     *
     * @param timeSlotId The ID of the time slot to be marked as non-available.
     */
    public void markTimeSlotAsNonAvailable(Integer timeSlotId) {
        String query = "UPDATE PATJODB.BOOKING SET AVAILABLE = FALSE WHERE BOOKING_ID = ?";
        jdbcTemplate.update(query, timeSlotId);
    }

}
