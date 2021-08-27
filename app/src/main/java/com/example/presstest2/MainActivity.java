package com.example.presstest2;

import static com.example.presstest2.CalendarUtils.daysInMonthArray;
import static com.example.presstest2.CalendarUtils.daysInMonthArray2;
import static com.example.presstest2.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    //------monthView and dayView------
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    //------monthView and dayView------


    //------eventView------
    MyAdapter myAdapter;
    ArrayList<ParentItem> parentItemArrayList;
    ArrayList<ChildItem> childItemArrayList;
    RecyclerView RVparent;
    //------eventView------


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------eventControl------
        RVparent = findViewById(R.id.RVparent);
        parentItemArrayList = new ArrayList<>();
        childItemArrayList = new ArrayList<>();

        myAdapter =new MyAdapter(this,parentItemArrayList,childItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RVparent.setLayoutManager(linearLayoutManager);
        RVparent.setAdapter(myAdapter);
        //------eventControl------

        //------parentView (whenday and date)---------------------------
        String[] whenday = {"Today","Tomorrow","the day after tomorrow"};
        String[] whenDate = {"2021/08/25","2021/08/26","2021/08/27"};

        //set parentView
        for(int i =0;i <whenday.length;i++){
            ParentItem parentItem = new ParentItem(whenday[i],whenDate[i]);
            parentItemArrayList.add(parentItem);
        }
        //------parentView (whenday and date)---------------------------

        //------childView (eventTitle and eventDateTime)-----------------

        //eventDataControl (要再加年份去做設定迴圈)
        String[] Title = {"Burger","Machurain","Sandwich"};
        String[] EventTime = {"8:00-9:00","10:00-16:00","19:00-20:00"};
        String[] eventKind = {"work","course","work"};

        //eventKindGraph and tag
        int[] kindOfEventG = {R.drawable.course,R.drawable.ic_work,R.drawable.event};
        String[] tag = {"course","work","schedule"};

        //to Control null condition
        String[] Null = {"","",""};
        int[] nullint={R.drawable.indicator1,R.drawable.indicator1,R.drawable.indicator1};

        //set childView
        for(int j=0;j< Title.length;j++) {
            if (eventKind[j].equals(tag[0]))//course
            {
                ChildItem childItem1 = new ChildItem(Title[j], EventTime[j], kindOfEventG[0]);
                childItemArrayList.add(childItem1);
            } else if (eventKind[j].equals(tag[1]))//work
            {
                ChildItem childItem2 = new ChildItem(Title[j], EventTime[j], kindOfEventG[1]);
                childItemArrayList.add(childItem2);
            } else if (eventKind[j].equals(tag[2]))//schedule
            {
                ChildItem childItem3 = new ChildItem(Title[j], EventTime[j], kindOfEventG[2]);
                childItemArrayList.add(childItem3);
            }
            else//null
                {
                ChildItem childItem4 = new ChildItem(Null[j], Null[j], nullint[j]);
                childItemArrayList.add(childItem4);
            }
        }
        //------childView (eventTitle and eventDateTime)-----------------

        //pull up windows
        CollapsingToolbarLayout collapsingToolbarLayout= findViewById(R.id.collapsing_toolbar);

        //------set bottomAppBar null to set background(selector)--------------------------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setItemIconTintList(null);
        //------set bottomAppBar null to set background(selector)--------------------------
        //        控制功能列
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.home:
//
//
//
//
//                }
//                return false;
//            }
//        });

        //------set FAB null to set background(selector)--------------------------
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setImageTintList(null);
        //------set FAB null to set background(selector)--------------------------

        //------set Button null to set background--------------------------
        Button br = findViewById(R.id.br);
        Button bl = findViewById(R.id.bl);
        br.setBackgroundTintList(null);
        bl.setBackgroundTintList(null);
        //------set Button null to set background--------------------------


        //get now time
        CalendarUtils.selectedDate= LocalDate.now();
        initWidgets();
        setMonthView();
    }

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

//    unused code(weekView
//        public void weeklyAction(View view) {
//        startActivity(new Intent(this,WeekViewActivity.class));
//    }
}