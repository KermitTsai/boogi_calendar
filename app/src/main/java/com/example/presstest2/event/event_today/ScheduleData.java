package com.example.presstest2.event.event_today;

public class ScheduleData {
    private String title;
    private String date;
    private String beginTime;
    private String endTime;
    private String kind;
    private String color;


    public ScheduleData(){

    }

    public ScheduleData(String title, String date,String beginTime, String endTime,String kind,String color){
        this.title = title;
        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.kind = kind;
        this.color = color;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
