package com.example.presstest2.calendar;

import static com.example.presstest2.calendar.CalendarUtils.daysInMonthArray;
import static com.example.presstest2.calendar.CalendarUtils.daysInMonthArray2;
import static com.example.presstest2.calendar.CalendarUtils.monthYearFromDate;
import static com.example.presstest2.calendar.CalendarUtils.selectedDate;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.presstest2.FirstPage.Register;
import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.example.presstest2.event.event_af_to.EventAdapter_af_to;
import com.example.presstest2.event.event_af_to.Event_af_to;
import com.example.presstest2.event.event_today.EventEditActivity;
import com.example.presstest2.event.event_today.Event_today;
import com.example.presstest2.event.event_today.EventAdapter_today;
import com.example.presstest2.event.event_today.addevent;
import com.example.presstest2.event.event_tomorrow.EventAdapter_tomorrow;
import com.example.presstest2.event.event_tomorrow.Event_tomorrow;
import com.example.presstest2.merge.MergeMainActivity;
import com.example.presstest2.settings.settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    //------monthView and dayView------
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    //firebase
    private FirebaseAuth fAuth;
    private String user;
    private FirebaseFirestore fStore;

    private TextView textViewData;




    //control calendar bg
    AppBarLayout appBarLayout;

    //test
    public RecyclerView rcvEventToday;
    public static EventAdapter_today adapter_today;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser().getEmail();


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

                    case R.id.miMerge:
                        startActivity(new Intent(getApplicationContext(), MergeMainActivity.class));
                        overridePendingTransition(0,0);
                        return false;

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
//        RecyclerView rcvEventToday =findViewById(R.id.rcv_today); initweigets
//        LinearLayoutManager linearLayoutManager_today = new LinearLayoutManager(this); init
//        rcvEventToday.setLayoutManager(linearLayoutManager_today); init
//        EventAdapter_today adapter_today =new EventAdapter_today(getListEventsToday());
//        rcvEventToday.setAdapter(adapter_today);

//        //event_Tomorrow
//        RecyclerView rcvEventTomorrow =findViewById(R.id.rcv_tomorrow);
//        LinearLayoutManager linearLayoutManager_tomorrow = new LinearLayoutManager(this);
//        rcvEventTomorrow.setLayoutManager(linearLayoutManager_tomorrow);
//        EventAdapter_tomorrow adapter_tomorrow =new EventAdapter_tomorrow(getListEventsTomorrow());
//        rcvEventTomorrow.setAdapter(adapter_tomorrow);
//
//        //event_After_Tomorrow
//        RecyclerView rcvEventAfterTomorrow =findViewById(R.id.rcv_after_tomorrow);
//        LinearLayoutManager linearLayoutManager_af_to = new LinearLayoutManager(this);
//        rcvEventAfterTomorrow.setLayoutManager(linearLayoutManager_af_to);
//        EventAdapter_af_to adapter_af_to=new EventAdapter_af_to(getListEventsAfterTomorrow());
//        rcvEventAfterTomorrow.setAdapter(adapter_af_to);





//        橫線
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        rcvUser.addItemDecoration(itemDecoration);



//        test
//        fStore.collection("users").document(user).collection("Schedule")
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                 @Override
//                                                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                     String data = "";
//                                                     if (task.isSuccessful()) {
//                                                         for (QueryDocumentSnapshot document : task.getResult()) {
//                                                             if (document.exists()) {
//                                                                 Map<String, Object> map = document.getData();
//                                                                 Set set = map.keySet();
//                                                                 Object[] arr = set.toArray();
//                                                                 Arrays.sort(arr);
//                                                                 for (int i = 0; i < arr.length; i++) {
//                                                                     data += document.getId()+arr[i] + ": " + map.get(arr[i]) + "\n";
//                                                                 }
//                                                                 textViewData.setText("DocumentSnapshot data: \n" + data);
//                                                             }
//                                                         }
//                                                     }
//                                                 }
//                                             });


        fStore.collection("users").document(user).collection("Schedule")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String data = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists())//arr欄位 map.get(arr[i])欄位值
                                {
                                    Map<String, Object> mapData = document.getData();
                                    Set set = mapData.keySet();
                                    Object[] arr = set.toArray();
                                    Arrays.sort(arr);
                                    if (addevent.idList.size() == 0) {
                                        for (int i = 0; i < arr.length; i += 6) {
                                            Event_today.eventsList.add(new Event_today(document.getId(), mapData.get(arr[i + 5]).toString(), mapData.get(arr[i + 2]).toString(), mapData.get(arr[i]).toString() + "-" + mapData.get(arr[i + 3]).toString(), mapData.get(arr[i + 4]).toString(), mapData.get(arr[i + 1]).toString()));
                                            Log.i("sss", "CreateifSuccessful"+document.getId());
                                        }
                                    }
                                    else {
                                        if (!addevent.idList.contains(document.getId())) {
                                            for (int i = 0; i < arr.length; i += 6) {
                                                Event_today.eventsList.add(new Event_today(document.getId(), mapData.get(arr[i + 5]).toString(), mapData.get(arr[i + 2]).toString(), mapData.get(arr[i]).toString() + "-" + mapData.get(arr[i + 3]).toString(), mapData.get(arr[i + 4]).toString(), mapData.get(arr[i + 1]).toString()));
                                                Log.i("sss", "CreateelseSuccessful"+document.getId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

        fStore.collection("users").document(user).collection("Schedule")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                String id=document.getId();
                                if (!addevent.idList.contains(document.getId())){
                                    addevent.idList.add(id);
                                    Log.d("sss", 111111+" "+id + " => " + document.getData());
                                }
                            }
                        }
                        else{
                            Log.w("sss", "Error getting documents.", task.getException());

                        }
                    }
                });

        //get now time
        CalendarUtils.selectedDate= LocalDate.now();
        initWidgets();
        setMonthView();



    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //---------------------getDataBlock-------------------------------
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        rcvEventToday =findViewById(R.id.rcv_today);
        LinearLayoutManager linearLayoutManager_today = new LinearLayoutManager(this);
        rcvEventToday.setLayoutManager(linearLayoutManager_today);
        textViewData = findViewById(R.id.TextViewData);
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

        setEventAdapter();
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



    private void setEventAdapter() {
        ArrayList<Event_today> dailyEvents =  Event_today.eventsForDate(CalendarUtils.selectedDate);
        adapter_today =new EventAdapter_today(dailyEvents);
        rcvEventToday.setAdapter(adapter_today);


    }


    public void newEventAction(View view) {
        startActivity(new Intent(getApplicationContext(), addevent.class));

    }
}