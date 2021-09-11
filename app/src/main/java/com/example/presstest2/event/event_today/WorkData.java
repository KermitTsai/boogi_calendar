package com.example.presstest2.event.event_today;

public class WorkData {
    private String title;
    private String beginTime;
    private String endTime;
    private String year;
    private String date;
    private String day;
    private String workType;
    private String workTime;
    private String wage;



    public WorkData(){

    }

    public WorkData(String title, String beginTime, String endTime, String year, String date, String day, String workType, String workTime, String wage){
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.year = year;
        this.date = date;
        this.day = day;
        this.workType = workType;
        this.workTime = workTime;
        this.wage = wage;


    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getDay() { return day; }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getWorkType() { return workType; }

    public String getWorkTime() { return workTime; }

    public String getWage() { return wage; }


}
