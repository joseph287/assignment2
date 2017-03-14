package edu.montclair.mobilecomputing.mymac.assignment_1.util;

/**
 * Created by Admin on 11-03-2017.
 */
public class NotesContainer {
    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String title;
    String description;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    long date;
}
