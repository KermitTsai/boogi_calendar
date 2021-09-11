package com.example.presstest2.event.event_today;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presstest2.R;
import com.example.presstest2.calendar.CalendarUtils;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private EditText eventDateET;
    private EditText eventTimeET;
    private EditText eventKindET;
    private EditText eventColorET;

//    private TextView eventDateTV,eventTimeTV;
//    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

    }

    private void initWidgets() {
        eventTimeET = findViewById(R.id.eventTimeET);
        eventNameET = findViewById(R.id.eventNameET);
        eventKindET = findViewById(R.id.eventKindET);
        eventColorET = findViewById(R.id.eventColorET);
        eventDateET = findViewById(R.id.eventDateET);


    }

    public void save(View view){
        String eventTime = eventTimeET.getText().toString();
        String eventName = eventNameET.getText().toString();
        String eventKind = eventKindET.getText().toString();
        String eventColor = eventColorET.getText().toString();
        String eventDate = eventDateET.getText().toString();

//        Event_today newEvent = new Event_today(eventTime,eventName,eventKind,eventColor,eventDate);
//        Event_today.eventsList.add(newEvent);
        finish();

    }



}
