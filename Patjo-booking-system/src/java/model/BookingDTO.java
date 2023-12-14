package model;

import java.io.Serializable;

/**
 *
 *
 */
public class BookingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String typeOfSession;
    private String location;
    private String startTime;

    public BookingDTO(int id, String typeOfSession, String location, String startTime) {
        this.id = id;
        this.typeOfSession = typeOfSession;
        this.location = location;
        this.startTime = startTime;
    }

    public BookingDTO() {

    }

    public String getTypeOfSession() {
        return typeOfSession;
    }

    public void setTypeOfSession(String typeOfSession) {
        this.typeOfSession = typeOfSession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}