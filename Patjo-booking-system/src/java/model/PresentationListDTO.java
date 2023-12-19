package model;

import java.io.Serializable;

/**
 *
 * 
 */
public class PresentationListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int listId;
    private String courseName;
    private String creatorName;
    
    public PresentationListDTO(int listId, String courseName, String creatorName){
    this.listId = listId;
    this.courseName = courseName;
    this.creatorName = creatorName;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    
    
}
