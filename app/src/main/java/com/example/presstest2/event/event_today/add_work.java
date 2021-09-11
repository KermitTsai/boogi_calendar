package com.example.presstest2.event.event_today;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.presstest2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class add_work extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    EditText workname;
    Button beginbtn,endbtn,securebtn;
    TextView selectMonth,selectDayBig,selectDaySmall,selectDayFeb,selectDayLeapFeb,selectWorkType;
    EditText ed1,ed3,ed4;
    TextInputLayout edLayout1,edLayout3,edLayout4;
    TextView ed2;
    TextView textViewError;
    String[] monthArray;
    String[] monthDaySmall,monthDayBig,monthDayFeb,monthDayLeapFeb ;
    String[] workType;
    //年份
    String[] yearArray;
    TextView selectYear;

    //判斷leap month
    Calendar leap = Calendar.getInstance();
    int leapyear;
    //取工作時數
    String getWorkTime;
    LinearLayout LinearLayoutWorkDay;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference UsersRef = db.collection("users");
    private FirebaseAuth fAuth;
    private String user;

    private int indexYear;
    private int indexMonth;
    private int indexWorkType;

    private CharSequence[] colorSequence ;
    private int colornumber;
    private TextView choose_event_color;
//    String pt_money,pt_time,day_money,month_money;


    boolean[] selectWorkDay;
    int beginTimeHour,beginTimeMinute,endTimeHour,endTimeMinute;
    double pt_worktime;

    BottomNavigationView navigationView ;

    ArrayList<Integer> DayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        //pony
        leapyear = leap.get(Calendar.YEAR);

        //firebase
        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser().getEmail();

        navigationView=findViewById(R.id.kind_navigation_work);
        navigationView.setBackgroundTintList(null);


        navigationView.setSelectedItemId(R.id.navi_work);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navi_event:
                        startActivity(new Intent(getApplicationContext(), addevent.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.navi_course:
                        startActivity(new Intent(getApplicationContext(), add_course.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.navi_work:

                        return false;
                }
                return false;
            }
        });
        constraintLayout = findViewById(R.id.constraint_work);
        workname = findViewById(R.id.add_work_name);
        beginbtn = findViewById(R.id.add_work_begintime);
        endbtn = findViewById(R.id.add_work_endtime);
        securebtn = findViewById(R.id.add_event_secure);

        selectMonth = findViewById(R.id.singleSelect);
        selectDayBig = findViewById(R.id.t_big);
        selectDaySmall = findViewById(R.id.t_small);
        selectDayFeb = findViewById(R.id.t_feb);
        selectDayLeapFeb = findViewById(R.id.t_leapfeb);
        selectWorkType = findViewById(R.id.work_chooseType);
        selectYear = findViewById(R.id.singleSelectYear);

        ed1 = findViewById(R.id.work_salary_PT);
        ed2 = findViewById(R.id.work_salary_PThour);
        ed3 = findViewById(R.id.work_salary_Day);
        ed4 = findViewById(R.id.work_salary_Month);

        textViewError = findViewById(R.id.textViewError_work);

        edLayout1 = findViewById(R.id.hourLayout);
        edLayout3 = findViewById(R.id.dayLayout);
        edLayout4 = findViewById(R.id.monthLayout);
        choose_event_color = findViewById(R.id.add_event_chooseColor);

        LinearLayoutWorkDay = findViewById(R.id.LineaLayout_work_day);



        //---藏鍵盤事件---
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        workname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showKeyBoard(workname);
                //get value from edittext
                String s = workname.getText().toString().trim();
                textViewError.setText("");//更新錯誤訊息
                //check condition
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //when action is equal to action done
                    //hide keyboard
                    hideKeyBoard(workname);
                    //display toast

                    return true;
                }

                return false;
            }
        });

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch is detected");

                int eventType = event.getActionMasked();

                switch (eventType){
                    case MotionEvent.ACTION_DOWN:
                        hideKeyBoard(workname);
                        break;
                }

                return true;
            }
        });
        //---藏鍵盤事件end---
        selectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearArray = new String[]{"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取年份").setIcon(R.drawable.ic_baseline_calendar_today_24).setCancelable(false)
                        .setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setSingleChoiceItems(yearArray, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                indexYear=which;
                            }
                        }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectYear.setText(yearArray[indexYear]);
                                textViewError.setText("");//更新錯誤訊息

                                if(!TextUtils.isEmpty(selectMonth.getText().toString())){
                                    if(selectMonth.getText()=="1月"||selectMonth.getText()=="3月"||selectMonth.getText()=="5月"
                                            ||selectMonth.getText()=="7月"||selectMonth.getText()=="8月"||
                                            selectMonth.getText()=="10月"||selectMonth.getText()=="12月"){
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayBig.setClickable(true);
                                        selectDayBig.setVisibility(View.VISIBLE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }
                                    else if(selectMonth.getText()=="4月"||selectMonth.getText()=="6月"||selectMonth.getText()=="9月"
                                            ||selectMonth.getText()=="11月") {
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDaySmall.setClickable(true);
                                        selectDaySmall.setVisibility(View.VISIBLE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }
                                    //pony
                                    else if(Integer.parseInt(selectYear.getText().toString())%4==0&&selectMonth.getText()=="2月"){
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayLeapFeb.setClickable(true);
                                        selectDayLeapFeb.setVisibility(View.VISIBLE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                    }
                                    else{
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayFeb.setClickable(true);
                                        selectDayFeb.setVisibility(View.VISIBLE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }

                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //not finish code
                            }
                        });
                builder.show();
            }
        });

        selectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthArray = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取月份").setIcon(R.drawable.ic_baseline_calendar_today_24).setCancelable(true)
                        .setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setSingleChoiceItems(monthArray, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                indexMonth=which;

                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if(TextUtils.isEmpty(selectYear.getText().toString())){
                                    textViewError.setTextColor(Color.rgb(251,139,36));
                                    textViewError.setText("請先選取年份！");
                                    return;

                                }else{
                                    selectMonth.setText(monthArray[indexMonth]);
                                    textViewError.setText("");//更新錯誤訊息

                                    if(selectMonth.getText()=="1月"||selectMonth.getText()=="3月"||selectMonth.getText()=="5月"
                                            ||selectMonth.getText()=="7月"||selectMonth.getText()=="8月"||
                                            selectMonth.getText()=="10月"||selectMonth.getText()=="12月"){
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayBig.setClickable(true);
                                        selectDayBig.setVisibility(View.VISIBLE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }
                                    else if(selectMonth.getText()=="4月"||selectMonth.getText()=="6月"||selectMonth.getText()=="9月"
                                            ||selectMonth.getText()=="11月") {
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDaySmall.setClickable(true);
                                        selectDaySmall.setVisibility(View.VISIBLE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }
                                    //pony
                                    else if(Integer.parseInt(selectYear.getText().toString())%4==0&&selectMonth.getText()=="2月"){
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayLeapFeb.setClickable(true);
                                        selectDayLeapFeb.setVisibility(View.VISIBLE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDayFeb.setVisibility(View.GONE);
                                    }
                                    else{
                                        LinearLayoutWorkDay.setVisibility(View.VISIBLE);
                                        selectDayFeb.setClickable(true);
                                        selectDayFeb.setVisibility(View.VISIBLE);
                                        selectDayBig.setVisibility(View.GONE);
                                        selectDaySmall.setVisibility(View.GONE);
                                        selectDayLeapFeb.setVisibility(View.GONE);
                                    }
                                }

                            }
                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        selectDayBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDayBig = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                selectWorkDay = new boolean[monthDayBig.length];
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取工作日(可多選)").setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded)).setCancelable(true)
                        .setMultiChoiceItems(monthDayBig, selectWorkDay, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(!DayList.contains(which))//避免選過一次 第二次選又將重複數字加入陣列
                                {
                                    if (isChecked)
                                    {
                                        DayList.add(which);
                                        Collections.sort(DayList);
                                    } else
                                        {
                                        DayList.remove(DayList.indexOf(which));

                                    }
                                }
                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder stringBuilder = new StringBuilder();
                                //use for loop
                                for (int i = 0; i < DayList.size(); i++) {
                                    //concat the value
                                    stringBuilder.append(monthDayBig[DayList.get(i)]);
                                    //check condition
                                    if (i != DayList.size() - 1) {
                                        //when i value not equal to month list size -1
                                        //add commma
                                        stringBuilder.append(",");
                                    }
                                }
                                //set text on textview
                                selectDayBig.setText(stringBuilder.toString());
                                textViewError.setText("");//更新錯誤訊息

                            }
                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>清除全部</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < selectWorkDay.length; i++) {
                                    //remove all selection
                                    selectWorkDay[i] = false;
                                    //clear day list
                                    DayList.clear();
                                    //clear text view value
                                    selectDayBig.setText("");
                                }
                            }
                        });
                builder.show();
            }
        });

        selectDaySmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDaySmall = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
                selectWorkDay = new boolean[monthDaySmall.length];
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取工作日(可多選)").setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded)).setCancelable(true)
                        .setMultiChoiceItems(monthDaySmall, selectWorkDay, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(!DayList.contains(which)){
                                    if (isChecked) {
                                        DayList.add(which);
                                        Collections.sort(DayList);
                                    } else {
                                        DayList.remove(DayList.indexOf(which));

                                    }
                                }
                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>") ,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder stringBuilder = new StringBuilder();
                                //use for loop
                                for (int i = 0; i < DayList.size(); i++) {
                                    //concat the value
                                    stringBuilder.append(monthDaySmall[DayList.get(i)]);
                                    //check condition
                                    if (i != DayList.size() - 1) {
                                        //when i value not equal to month list size -1
                                        //add commma
                                        stringBuilder.append(",");
                                    }
                                }
                                //set text on textview
                                selectDaySmall.setText(stringBuilder.toString());
                                textViewError.setText("");//更新錯誤訊息
                            }
                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>清除全部</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < selectWorkDay.length; i++) {
                                    //remove all selection
                                    selectWorkDay[i] = false;
                                    //clear day list
                                    DayList.clear();
                                    //clear text view value
                                    selectDaySmall.setText("");
                                }
                            }
                        });
                builder.show();
            }
        });

        selectDayFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDayFeb = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
                selectWorkDay = new boolean[monthDayFeb.length];
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取工作日(可多選)").setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded)).setCancelable(true)
                        .setMultiChoiceItems(monthDayFeb, selectWorkDay, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(!DayList.contains(which)){
                                    if (isChecked) {
                                        DayList.add(which);
                                        Collections.sort(DayList);
                                    } else {
                                        DayList.remove(DayList.indexOf(which));

                                    }
                                }
                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder stringBuilder = new StringBuilder();
                                //use for loop
                                for (int i = 0; i < DayList.size(); i++) {
                                    //concat the value
                                    stringBuilder.append(monthDayFeb[DayList.get(i)]);
                                    //check condition
                                    if (i != DayList.size() - 1) {
                                        //when i value not equal to month list size -1
                                        //add commma
                                        stringBuilder.append(",");
                                    }
                                }
                                //set text on textview
                                selectDayFeb.setText(stringBuilder.toString());
                                textViewError.setText("");//更新錯誤訊息
                            }
                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>清除全部</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < selectWorkDay.length; i++) {
                                    //remove all selection
                                    selectWorkDay[i] = false;
                                    //clear day list
                                    DayList.clear();
                                    //clear text view value
                                    selectDayFeb.setText("");
                                }
                            }
                        });
                builder.show();
            }
        });

        selectDayLeapFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDayLeapFeb = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28","29"};
                selectWorkDay = new boolean[monthDayLeapFeb.length];
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取工作日(可多選)").setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded)).setCancelable(true)
                        .setMultiChoiceItems(monthDayLeapFeb, selectWorkDay, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(!DayList.contains(which)){
                                    if (isChecked) {
                                        DayList.add(which);
                                        Collections.sort(DayList);
                                    } else {
                                        DayList.remove(DayList.indexOf(which));

                                    }
                                }
                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder stringBuilder = new StringBuilder();
                                //use for loop
                                for (int i = 0; i < DayList.size(); i++) {
                                    //concat the value
                                    stringBuilder.append(monthDayLeapFeb[DayList.get(i)]);
                                    //check condition
                                    if (i != DayList.size() - 1) {
                                        //when i value not equal to month list size -1
                                        //add commma
                                        stringBuilder.append(",");
                                    }
                                }
                                //set text on textview
                                selectDayLeapFeb.setText(stringBuilder.toString());
                                textViewError.setText("");//更新錯誤訊息
                            }

                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>清除全部</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < selectWorkDay.length; i++) {
                                    //remove all selection
                                    selectWorkDay[i] = false;
                                    //clear day list
                                    DayList.clear();
                                    //clear text view value
                                    selectDayLeapFeb.setText("");
                                }
                            }
                        });
                builder.show();
            }
        });


        selectWorkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workType = new String[]{"時薪","日薪","月薪"};
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_work.this)
                        .setTitle("選取工作型態").setCancelable(false).setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setSingleChoiceItems(workType, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                indexWorkType=which;



                            }
                        }).setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(TextUtils.isEmpty(beginbtn.getText().toString())||TextUtils.isEmpty(endbtn.getText().toString())){
                                    textViewError.setText("請選擇時間！");
                                    return;
                                }else if(beginTimeHour>endTimeHour){
                                    textViewError.setText("時間選擇有誤！");
                                    return;
                                }
                                else{
                                    selectWorkType.setText(workType[indexWorkType]);
                                    textViewError.setText("");//更新錯誤訊息
                                    if(selectWorkType.getText()=="時薪") {
                                        //計算工作時數
                                        int eh = endTimeHour; int bh = beginTimeHour; double worktimeminute = 0.000;
                                        int em = endTimeMinute; int bm = beginTimeMinute;
                                        String beginTime = beginbtn.getText().toString();
                                        String endTime = endbtn.getText().toString();

                                        if(eh>=bh){worktimeminute+= (eh-bh);}
                                        if (bm > em) {
                                            worktimeminute+= (60-bm+em)/60d;
                                            worktimeminute-=1 ;
                                        }
                                        else {
                                            worktimeminute += (em-bm)/60d;
                                        }

                                        double d = worktimeminute;
                                        String s = String.format("%.3f",d);
                                        getWorkTime=s;
                                        ed1.setVisibility(View.VISIBLE);
                                        edLayout1.setVisibility(View.VISIBLE);
                                        ed2.setVisibility(View.VISIBLE);
                                        ed2.setText("工作時數：" + s +"小時");
                                        ed3.setVisibility(View.GONE);
                                        edLayout3.setVisibility(View.GONE);
                                        ed4.setVisibility(View.GONE);
                                        edLayout4.setVisibility(View.GONE);
                                        ed3.setText("");
                                        ed4.setText("");
                                        pt_worktime = Double.parseDouble(s);
                                    }

                                    else if(selectWorkType.getText()=="日薪"){
                                        ed1.setVisibility(View.GONE);
                                        edLayout1.setVisibility(View.GONE);
                                        ed2.setVisibility(View.GONE);
                                        ed3.setVisibility(View.VISIBLE);
                                        edLayout3.setVisibility(View.VISIBLE);
                                        ed4.setVisibility(View.GONE);
                                        edLayout4.setVisibility(View.GONE);
                                        ed1.setText("");
                                        ed2.setText("");
                                        getWorkTime="";
                                        ed4.setText("");
                                    }
                                    else{
                                        ed1.setVisibility(View.GONE);
                                        edLayout1.setVisibility(View.GONE);
                                        ed2.setVisibility(View.GONE);
                                        ed3.setVisibility(View.GONE);
                                        edLayout3.setVisibility(View.GONE);
                                        ed4.setVisibility(View.VISIBLE);
                                        edLayout4.setVisibility(View.VISIBLE);
                                        ed1.setText("");
                                        ed2.setText("");
                                        getWorkTime="";
                                        ed3.setText("");
                                    }

                                }


                            }
                        }).setNeutralButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        //---color selection--------------
        colorSequence = new CharSequence[]{"red","orange","yellow","teal","green","blue","navy","light purple","none"};
//        choose_event_color.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(add_work.this);
//                builder.setTitle("選取顏色").setCancelable(true).setSingleChoiceItems(colorSequence, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        colornumber = which;
//                    }
//                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(colornumber==0){choose_event_color.setBackgroundResource(R.color.red);choose_event_color.setText("red");}
//                        else if(colornumber==1){choose_event_color.setBackgroundResource(R.color.orange);choose_event_color.setText("orange");}
//                        else if(colornumber==2){choose_event_color.setBackgroundResource(R.color.yellow);choose_event_color.setText("yellow");}
//                        else if(colornumber==3){choose_event_color.setBackgroundResource(R.color.teal_200);choose_event_color.setText("teal");}
//                        else if(colornumber==4){choose_event_color.setBackgroundResource(R.color.green);choose_event_color.setText("green");}
//                        else if(colornumber==5){choose_event_color.setBackgroundResource(R.color.blue);choose_event_color.setText("blue");}
//                        else if(colornumber==6){choose_event_color.setBackgroundResource(R.color.navy);choose_event_color.setText("navy");}
//                        else if(colornumber==7){choose_event_color.setBackgroundResource(R.color.purple_200);choose_event_color.setText("purple");}
//                        else {choose_event_color.setBackgroundResource(R.color.white);choose_event_color.setTextColor(Color.rgb(0,0,0));choose_event_color.setText("none");}
//                    }
//                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();
//            }
//        });
        //------color selection end---------------------------------------------------------------------



        //ed1 ,ed2 , ed3, ed4 need to build gettext to store

        securebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workName = workname.getText().toString();
                String selectWorkYear = selectYear.getText().toString();
                String selectWorkMonth = selectMonth.getText().toString();
                String selectBigDay = selectDayBig.getText().toString();
                String selectFebDay = selectDayFeb.getText().toString();
                String selectLeapFebDay= selectDayLeapFeb.getText().toString();
                String selectSmallDay = selectDaySmall.getText().toString();
                String selectWorkTypeText = selectWorkType.getText().toString();
//                String selectWorkColor = choose_event_color.getText().toString();

                String beginTime = beginbtn.getText().toString();
                String endTime = endbtn.getText().toString();


                String ed1Text = ed1.getText().toString();
                String ed2Text = ed2.getText().toString();
                String ed3Text = ed3.getText().toString();
                String ed4Text = ed4.getText().toString();

                if(TextUtils.isEmpty(workName)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入工作名稱！");
                    return;
                }else if(TextUtils.isEmpty(selectWorkYear)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇年份！");
                    return;

                }
                else if(TextUtils.isEmpty(selectBigDay) && (selectWorkMonth == "1月" || selectWorkMonth == "3月" || selectWorkMonth == "5月"
                        || selectWorkMonth == "7月" || selectWorkMonth == "8月"
                        || selectWorkMonth == "10月" || selectWorkMonth == "12月")){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇日期！");
                    return;
                }
                else if(TextUtils.isEmpty(selectSmallDay) && (selectWorkMonth == "4月" || selectWorkMonth == "6月"
                        || selectWorkMonth == "9月" || selectWorkMonth == "11月")){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇日期！");
                    return;
                }

                else if(selectWorkMonth == "2月" && Integer.parseInt(selectYear.getText().toString())%4==0 &&TextUtils.isEmpty(selectLeapFebDay)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇日期！");
                    return;
                }
                else if( selectWorkMonth == "2月" && Integer.parseInt(selectYear.getText().toString())%4!=0 && TextUtils.isEmpty(selectFebDay) ){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇日期！");
                    return;
                }
                else if(endTimeHour<beginTimeHour){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("時間選擇有誤！");
                    return;
                }

                else if(TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("時間選擇有誤！");
                    return;
                }
                else if(TextUtils.isEmpty(selectWorkTypeText)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇工作型態！");
                    return;
                }


                else if(selectWorkTypeText == "時薪" && (TextUtils.isEmpty(ed1Text)||TextUtils.isEmpty(ed2Text))){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入時薪！");
                    return;
                }
                else if(selectWorkTypeText == "日薪" && (TextUtils.isEmpty(ed3Text))){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入日薪！");
                    return;
                }
                else if(selectWorkTypeText == "月薪" && (TextUtils.isEmpty(ed4Text))){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入月薪！");
                    return;
                }

                else{
                    if(selectWorkMonth == "1月" || selectWorkMonth == "3月" || selectWorkMonth == "5月"
                            || selectWorkMonth == "7月" || selectWorkMonth == "8月"
                            || selectWorkMonth == "10月" || selectWorkMonth == "12月"){
                        switch (selectWorkTypeText){
                            case "時薪":
                                WorkData workData1 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectBigDay, selectWorkTypeText, getWorkTime, ed1Text);
                                UsersRef.document(user).collection("Work").document().set(workData1, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "日薪":
                                WorkData workData2 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectBigDay, selectWorkTypeText, getWorkTime, ed3Text);
                                UsersRef.document(user).collection("Work").document().set(workData2, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "月薪":
                                WorkData workData3 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectBigDay, selectWorkTypeText, getWorkTime, ed4Text);
                                UsersRef.document(user).collection("Work").document().set(workData3, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                        }
                    }
                    else if(selectWorkMonth == "4月" || selectWorkMonth == "6月" || selectWorkMonth == "9月" || selectWorkMonth == "11月"){
                        switch (selectWorkTypeText){
                            case "時薪":
                                WorkData workData1 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectSmallDay, selectWorkTypeText, getWorkTime, ed1Text);
                                UsersRef.document(user).collection("Work").document().set(workData1, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "日薪":
                                WorkData workData2 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectSmallDay, selectWorkTypeText, getWorkTime, ed3Text);
                                UsersRef.document(user).collection("Work").document().set(workData2, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "月薪":
                                WorkData workData3 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectSmallDay, selectWorkTypeText, getWorkTime, ed4Text);
                                UsersRef.document(user).collection("Work").document().set(workData3, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                        }
                    }
                    else if(Integer.parseInt(selectWorkYear)%4 == 0&&selectWorkMonth == "2月" ){
                        switch (selectWorkTypeText){
                            case "時薪":
                                WorkData workData1 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectLeapFebDay, selectWorkTypeText, getWorkTime, ed1Text);
                                UsersRef.document(user).collection("Work").document().set(workData1, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "日薪":
                                WorkData workData2 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectLeapFebDay, selectWorkTypeText, getWorkTime, ed3Text);
                                UsersRef.document(user).collection("Work").document().set(workData2, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "月薪":
                                WorkData workData3 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectLeapFebDay, selectWorkTypeText, getWorkTime, ed4Text);
                                UsersRef.document(user).collection("Work").document().set(workData3, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                        }

                    }

                    else{
                        switch (selectWorkTypeText){
                            case "時薪":
                                WorkData workData1 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectFebDay, selectWorkTypeText, getWorkTime, ed1Text);
                                UsersRef.document(user).collection("Work").document().set(workData1, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "日薪":
                                WorkData workData2 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectFebDay, selectWorkTypeText, getWorkTime, ed3Text);
                                UsersRef.document(user).collection("Work").document().set(workData2, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                            case "月薪":
                                WorkData workData3 = new WorkData(workName, beginTime, endTime,selectWorkYear, selectWorkMonth, selectFebDay, selectWorkTypeText, getWorkTime, ed4Text);
                                UsersRef.document(user).collection("Work").document().set(workData3, SetOptions.merge());
                                textViewError.setTextColor(Color.rgb(97,155,138));
                                textViewError.setText("加入成功！");

                        }
                    }
                }

                finish();
            }
        });
    }

    public void selectBeginTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectHour, int selectMinute) {
                beginTimeHour = selectHour;
                beginTimeMinute = selectMinute;
                beginbtn.setText(String.format(Locale.getDefault(),"%02d : %02d",beginTimeHour,beginTimeMinute));
                textViewError.setText("");//更新錯誤訊息
                //更新工作時數
                    if(!(TextUtils.isEmpty(endbtn.getText().toString()))){
                        if(!TextUtils.isEmpty(selectWorkType.getText().toString())){
                            if(!(endTimeHour<beginTimeHour)){
                                int eh = endTimeHour; int bh = beginTimeHour; double worktimeminute = 0.000;
                                int em = endTimeMinute; int bm = beginTimeMinute;
                                String beginTime = beginbtn.getText().toString();
                                String endTime = endbtn.getText().toString();
                                if(eh>=bh){worktimeminute+= (eh-bh);}
                                if (bm > em) {

                                    worktimeminute+= (60-bm+em)/60d;
                                    worktimeminute-=1 ;
                                }
                                else {
                                    worktimeminute += (em-bm)/60d;
                                }
                                double d = worktimeminute;
                                String s = String.format("%.3f",d);
                                ed2.setText("工作時數：" + s +"小時");
                                getWorkTime=s;
                            }
                            else{
                                ed2.setText("時間選擇有誤！");
                                getWorkTime="";
                            }

                        }

                    }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,beginTimeHour,beginTimeMinute,true);

        timePickerDialog.setTitle("選取時間");
        timePickerDialog.show();
    }

    public void selectEndTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectHour, int selectMinute) {
                endTimeHour = selectHour;
                endTimeMinute = selectMinute;
                endbtn.setText(String.format(Locale.getDefault(),"%02d : %02d",endTimeHour,endTimeMinute));
                textViewError.setText("");//更新錯誤訊息
                //更新工作時數
                if(!(endTimeHour<beginTimeHour)){
                    if(!(TextUtils.isEmpty(beginbtn.getText().toString()))){
                        if(!TextUtils.isEmpty(selectWorkType.getText().toString())){
                            int eh = endTimeHour; int bh = beginTimeHour; double worktimeminute = 0.000;
                            int em = endTimeMinute; int bm = beginTimeMinute;
                            String beginTime = beginbtn.getText().toString();
                            String endTime = endbtn.getText().toString();
                            if(eh>=bh){worktimeminute+= (eh-bh);}
                            if (bm > em) {
//                                        worktimeminute+= ((60- bm + em) %60/6*0.1);
                                worktimeminute+= (60-bm+em)/60d;
                                worktimeminute-=1 ;
                            }
                            else {
                                worktimeminute += (em-bm)/60d;
                            }
                            double d = worktimeminute;
                            String s = String.format("%.3f",d);
                            ed2.setText("工作時數：" + s +"小時");
                            getWorkTime=s;
                        }

                    }
                }
                else{
                    ed2.setText("時間選擇有誤！");
                    getWorkTime="";
                }

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,endTimeHour,endTimeMinute,true);

        timePickerDialog.setTitle("選取時間");
        timePickerDialog.show();
    }

    private void hideKeyBoard(EditText editText) {
        //initialize input manager
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //hide soft keyboard
        manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(),0);
    }

    private void showKeyBoard(EditText editText) {
        //initialize input manager
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //show soft keyboard
        manager.showSoftInput(editText.getRootView(),InputMethodManager.SHOW_IMPLICIT);
        //focus on edittext
        editText.requestFocus();
    }
}