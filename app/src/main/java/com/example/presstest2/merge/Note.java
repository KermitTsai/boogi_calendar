package com.example.presstest2.merge;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

public class Note {
    private String email;
    private String fName;
    private String ID;
    List<Map<String, String>> Course;

    public Note(){

    }


    @Exclude

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setEmail(String email){this.email = email;}

    public String getEmail(){
        return email;
    }

    public void setCourse(List<Map<String, String>> Course){
        this.Course = Course;
    }

    public List<Map<String, String>> getCourse() {
        return Course;
    }
}

