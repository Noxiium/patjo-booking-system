package service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookingRepository;

/**
 * The BookingService class manages the business logic for handling booking-related operations.
 * 
 */

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    /**
    * Retrieves the list of available courses and returns it.
    * 
    * @return List of available courses
     */
    public List<String> fetchAvailableCourses() {
        //List<String> courseList = bookingRepository.fetchAvailableCoursesFromDB();
        
        //Placeholder
        List<String> courseList = new ArrayList<>();
        courseList.add("ID1212");
        courseList.add("ID2000");
        
        return courseList;
    }
}
