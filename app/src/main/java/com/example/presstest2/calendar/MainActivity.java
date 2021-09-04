package com.example.presstest2.calendar;

import static com.example.presstest2.calendar.CalendarUtils.daysInMonthArray;
import static com.example.presstest2.calendar.CalendarUtils.daysInMonthArray2;
import static com.example.presstest2.calendar.CalendarUtils.monthYearFromDate;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.example.presstest2.event.event_af_to.EventAdapter_af_to;
import com.example.presstest2.event.event_af_to.Event_af_to;
import com.example.presstest2.event.event_today.Event_today;
import com.example.presstest2.event.event_today.EventAdapter_today;
import com.example.presstest2.event.event_tomorrow.EventAdapter_tomorrow;
import com.example.presstest2.event.event_tomorrow.Event_tomorrow;
import com.example.presstest2.settings.settings;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    //------monthView and dayView------
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    //control calendar bg
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        //pull up windows
        CollapsingToolbarLayout collapsingToolbarLayout= findViewById(R.id.collapsing_toolbar);

        //------set bottomAppBar null to set background(selector)--------------------------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setItemIconTintList(null);

        //------set bottomAppBar null to set background(selector)--------------------------
        //        控制功能列
        bottomNavigationView.setSelectedItemId(R.id.miHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.miHome:
                        return true;

                    case R.id.miSetting:
                        startActivity(new Intent(getApplicationContext(), settings.class));
                        overridePendingTransition(0,0);
                        return false;

                    case R.id.placeholder:
                        return false;

                }
                return false;
            }
        });

        //------set FAB null to set background(selector)--------------------------
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        //to set "add sign"
        floatingActionButton.setImageTintList(null);
        //to set "FAB" ring color
        floatingActionButton.setBackgroundTintList(null);
        //------set FAB null to set background(selector)--------------------------

        //------set Button null to set background--------------------------
        Button br = findViewById(R.id.br);
        Button bl = findViewById(R.id.bl);

        br.setBackgroundTintList(null);
        bl.setBackgroundTintList(null);
        //------set Button null to set background--------------------------

        //------set appBarLayout null to set background--------------------------
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setBackgroundTintList(null);


        //------set Button null to set background--------------------------------

        //event_Today
        RecyclerView rcvEventToday =findViewById(R.id.rcv_today);
        LinearLayoutManager linearLayoutManager_today = new LinearLayoutManager(this);
        rcvEventToday.setLayoutManager(linearLayoutManager_today);
        EventAdapter_today adapter_today =new EventAdapter_today(getListEventsToday());
        rcvEventToday.setAdapter(adapter_today);

        //event_Tomorrow
        RecyclerView rcvEventTomorrow =findViewById(R.id.rcv_tomorrow);
        LinearLayoutManager linearLayoutManager_tomorrow = new LinearLayoutManager(this);
        rcvEventTomorrow.setLayoutManager(linearLayoutManager_tomorrow);
        EventAdapter_tomorrow adapter_tomorrow =new EventAdapter_tomorrow(getListEventsTomorrow());
        rcvEventTomorrow.setAdapter(adapter_tomorrow);

        //event_After_Tomorrow
        RecyclerView rcvEventAfterTomorrow =findViewById(R.id.rcv_after_tomorrow);
        LinearLayoutManager linearLayoutManager_af_to = new LinearLayoutManager(this);
        rcvEventAfterTomorrow.setLayoutManager(linearLayoutManager_af_to);
        EventAdapter_af_to adapter_af_to=new EventAdapter_af_to(getListEventsAfterTomorrow());
        rcvEventAfterTomorrow.setAdapter(adapter_af_to);





//        橫線
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        rcvUser.addItemDecoration(itemDecoration);

//        test
//        emptyView =findViewById(R.id.empty_View);
//        if(getListEvents().isEmpty()){
//            rcvEvent.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//        }
//        else{
//            rcvEvent.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }


        //get now time
        CalendarUtils.selectedDate= LocalDate.now();
        initWidgets();
        setMonthView();







    }



    //---------------------getDataBlock-------------------------------
    //today
    private List<Event_today> getListEventsToday() {
        List<Event_today> list = new ArrayList<>();
        list.add(new Event_today("08:00~09:00","微積分","Course","yellow"));
        list.add(new Event_today("10:00~18:00","做愛做的事","Schedule","yellow"));
        list.add(new Event_today("19:00~20:00","看電影","Schedule","green"));
        list.add(new Event_today("21:00~23:00","打工","Merge","blue"));

        return list;
    }
    //tomorrow
    private List<Event_tomorrow> getListEventsTomorrow() {
        List<Event_tomorrow> list = new ArrayList<>();
        list.add(new Event_tomorrow("08:00~09:00","微積分","Course","yellow"));

        list.add(new Event_tomorrow("19:00~20:00","看電影","Schedule","green"));
        list.add(new Event_tomorrow("21:00~23:00","打工","Merge","blue"));

        return list;
    }
    //the day after tomorrow
    private List<Event_af_to> getListEventsAfterTomorrow() {
        List<Event_af_to> list = new ArrayList<>();
        list.add(new Event_af_to("08:00~09:00","微積分","Course","yellow"));
        list.add(new Event_af_to("10:00~18:00","做愛","Schedule","yellow"));
        list.add(new Event_af_to("21:00~23:00","打工","Merge","blue"));
        return list;
    }




    //---------------------getDataBlock-------------------------------
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);
        ArrayList<String> daysInMonth2 = daysInMonthArray2(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date!=null){
            CalendarUtils.selectedDate =date;
            setMonthView();
        }
    }

//    禁用返回鍵
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }


//    unused code(weekView
//        public void weeklyAction(View view) {
//        startActivity(new Intent(this,WeekViewActivity.class));
//    }
}