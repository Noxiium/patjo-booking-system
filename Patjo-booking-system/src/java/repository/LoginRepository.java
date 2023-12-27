package repository;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author patricialagerhult
 */
@Repository
public class LoginRepository {
    
      private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    
}
