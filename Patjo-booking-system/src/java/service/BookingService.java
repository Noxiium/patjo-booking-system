package service;

import java.util.ArrayList;
import java.util.List;
import model.CourseDTO;
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
    public List<CourseDTO> fetchAvailableCourses() {
        List<CourseDTO> courseList = bookingRepository.fetchAvailableCoursesFromDB();  
        
        for(CourseDTO course : courseList){
        System.out.println(course.getCourseName());
        System.out.println(course.getCourseId());
        
        }
        return courseList;
    }
}
