
package repository;

import org.springframework.beans.factory.annotation.Autowired;
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
    
}
