
package repository;

import java.util.ArrayList;
import java.util.List;
import model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* BookingRepository class manages interactions with the database for booking-related transactions.
 * 
 */

@Repository
public class BookingRepository {
    
     private final JdbcTemplate jdbcTemplate;
     
     @Autowired
     public BookingRepository(JdbcTemplate jdbcTemplate){
      this.jdbcTemplate = jdbcTemplate;
     }

    public List<CourseDTO> fetchAvailableCoursesFromDB() {
        String query = "SELECT COURSE_ID,COURSE_NAME FROM BOOKING.COURSE";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CourseDTO.class));
    }
    
}
