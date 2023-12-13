package repository;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void handleUserLogin() {

    }

    public void saveUser(User user) {
        String sql = "INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    public Integer fetchUserID(User user) {
        String query = "SELECT ID FROM APP.USERS WHERE USERNAME = ? AND PASSWORD = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, user.getUsername(), user.getPassword());
    }

    public Integer checkIfUserExistInDB(User user) {
        String query = "SELECT ID FROM APP.USERS WHERE USERNAME = ?";
        try {
            Integer userId = jdbcTemplate.queryForObject(query, Integer.class, user.getUsername());
            return userId;
        } catch (Exception e) {
            return -1; 
        }
    }
    
    //TODO method, check if password match

}
