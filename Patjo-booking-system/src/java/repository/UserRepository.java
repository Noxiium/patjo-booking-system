package repository;

import java.util.List;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
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

    public User findByUsername(String username) {
        String sql = "SELECT * FROM PATJODB.USERS WHERE usersname = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
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

}
