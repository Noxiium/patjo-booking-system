package model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;
    private int isAdmin;
    private String[] courseIds;
    private List<CourseDTO> courses;

    public User(){
        
    }
    public User(String username, String password, int isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    public User(String username, String password, int isAdmin, List<CourseDTO> courses) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.courses = courses;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public List<CourseDTO> getCourseIds(){
        return courses;
    }
    
    public void setCoursesList(List<CourseDTO> courses){
            this.courses = courses;
    }
    
    @Override
    public String toString(){
        return this.userId + "\n" +
                this.username + "\n" +
                this.password + "\n" +
                this.isAdmin + "\n" +
        "\n---------------------------------------\n";
    }
}