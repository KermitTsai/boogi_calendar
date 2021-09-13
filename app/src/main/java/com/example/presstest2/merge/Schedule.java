package com.example.presstest2.merge;

import java.util.List;
import java.util.Map;


public class Schedule {
    private String date;
    private String Time;
    List<Map<String, String>> DaySchedule;
    Map<String, String> Title;
    Map<String, String> Start;
    Map<String, String> End;

    public Schedule(){

    }

    public Schedule(String date , String Time, List<Map<String, String>> DaySchedule, Map<String, String> Title, Map<String, String> Start, Map<String, String> End){
        this.date = date;
        this.Time = Time;
        this.DaySchedule = DaySchedule;
        this.Title = Title;
        this.Start = Start;
        this.End = End;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return Time;
    }

    public List<Map<String, String>> getDaySchedule() {
        return DaySchedule;
    }

    public Map<String, String> getTitle() {
        return Title;
    }

    public Map<String, String> getStart() {
        return Start;
    }

    public Map<String, String> getEnd() {
        return End;
    }
}
