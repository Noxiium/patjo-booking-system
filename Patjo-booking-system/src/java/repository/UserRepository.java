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
        String sql = "INSERT INTO BOOKING.USERS (USERSNAME, PASSWORD, ADMIN) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getIsAdmin());
    }

    public Integer fetchUserID(User user) {
        String query = "SELECT ID FROM BOOKING.USERS WHERE USERSNAME = ? AND PASSWORD = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, user.getUsername(), user.getPassword());
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM BOOKING.users WHERE usersname = ?";
        
        try{
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("users_id"));
            user.setUsername(rs.getString("usersname"));
            user.setPassword(rs.getString("password"));
            user.setIsAdmin(rs.getInt("admin"));

            return user;
        });
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    
    public List<User> fetchAllUsersFromDB(){
        String sql = "SELECT * FROM BOOKING.USERS";
        
        try{
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("users_id"));
            user.setUsername(rs.getString("usersname"));
            user.setPassword(rs.getString("password"));
            user.setIsAdmin(rs.getInt("admin"));
            return user;
        });
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    
    //TODO method, check if password match

}
