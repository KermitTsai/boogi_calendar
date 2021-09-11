package com.example.presstest2.event.event_today;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event_today {

    public static ArrayList<Event_today> eventsList = new ArrayList<>();

    public static ArrayList<Event_today> eventsForDate(LocalDate date){

        ArrayList<Event_today> events = new ArrayList<>();
        String checkedDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        checkedDate =date.format(formatter);
//        for(int j=0;j<eventsList.size();j++){
//            for(int i =0;i<addevent.idList.size();i++){
//                if(!(eventsList.contains(addevent.idList.get(i))))
//                    if(eventsList.get(j).getDate().equals(checkedDate)){
//                        events.add(eventsList.get(j));
//                    }
//            }
//        }
        for(int j=0;j<eventsList.size();j++){
            if(eventsList.get(j).getDate().equals(checkedDate)){
                events.add((eventsList.get(j)));
            }
        }

        return events;
//

//        for(Event_today event:eventsList){
//            if(event.getDate().equals(checkedDate)){
//                events.add(event);
//            }
//        }
//        return events;

    }
    private String Id;
    private String Title;
    private String Date;
    private String Time;
    private String Kind;
    private String Color;

    public Event_today(String Id,String Title, String Date,String Time,String Kind,String Color) {
        this.Id =Id;
        this.Time = Time;
        this.Title = Title;
        this.Kind = Kind;
        this.Color = Color;
        this.Date = Date;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
