package com.example.presstest2.event.event_today;

public class Event_today {

    private String Time;
    private String Title;
    private String Kind;
    private String Color;

    public Event_today(String time, String Title,String Kind,String Color) {
        this.Time = time;
        this.Title = Title;
        this.Kind = Kind;
        this.Color = Color;
    }

    public String getTime(){ return Time; }
    public void setTime(){ this.Time = Time; }
    public String getTitle(){ return Title; }
    public void setTitle(String title){
        this.Title = title;
    }
    public String getKind(){ return Kind; }
    public void setKind(){ this.Kind = Kind; }
    public String getColor(){ return Color; }
    public void setColor(){this.Color = Color;}



}
