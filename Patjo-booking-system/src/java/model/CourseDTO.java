package model;

import java.io.Serializable;


public class CourseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int courseId;

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
