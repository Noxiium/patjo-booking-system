package model;

import java.io.Serializable;
import javax.persistence.Column;

public class CourseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Column(name = "COURSE_ID")
    private int courseId;

    //@Column(name = "COURSE_NAME")
    private String courseName;

    public CourseDTO() {

    }

    public CourseDTO(String courseName, int courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    
}
