package com.example.presstest2.event.event_today;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.presstest2.R;
import com.example.presstest2.calendar.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class addevent extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference UsersRef = db.collection("users");
    //..
    private FirebaseAuth fAuth;
    private String user;


    private String title,remark;
    private Button add_event_begin_time,add_event_end_time;
    private TextView choose_event_color,choose_event_notification,add_event_secure,add_event_date;
    private EditText add_event_name,add_event_remark;
    private TextView textViewError;
    //---color selection 全域變數---
    private CharSequence[] colorSequence ;
    private int colornumber;
    //---date---
    private int year1,month1,date1;
    private String test1;
    //---notification---
    private CharSequence[] notificationTimeSequence;
    //---begin & end time setting-----
    private int beginTimeHour,beginTimeMinute,endTimeHour,endTimeMinute;
    ///
    private String ScheduleName;//
    private String ScheduleTime;//
    private String ScheduleColor;//
    private String ScheduleDate;//
//    private String[] MonthList={"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String[] MonthList={"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
    private String[] ColorList={"red","orange","yellow","teal","green","blue","navy","purple","none"};

    public static ArrayList<String> idList=new ArrayList<>();

    private ConstraintLayout lEvent;






    //view
    BottomNavigationView navigationView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        //firebase
        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser().getEmail();


        navigationView=findViewById(R.id.kind_navigation);
        navigationView.setBackgroundTintList(null);

        navigationView.setSelectedItemId(R.id.navi_event);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navi_event:

                        return false;

                    case R.id.navi_course:
                        item.setIconTintList(getColorStateList(R.color.gray));
                        startActivity(new Intent(getApplicationContext(), add_course.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.navi_work:
                        startActivity(new Intent(getApplicationContext(), add_work.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        navigationView=findViewById(R.id.kind_navigation);
        navigationView.setSelectedItemId(R.id.navi_event);


        add_event_begin_time = findViewById(R.id.add_event_beginTime);
        add_event_end_time = findViewById(R.id.add_event_endTime);
        add_event_date = findViewById(R.id.add_event_pickdate);
        choose_event_color = findViewById(R.id.add_event_chooseColor);
        choose_event_notification = findViewById(R.id.add_event_notification);
        add_event_secure = findViewById(R.id.add_event_secure);
        add_event_name = findViewById(R.id.add_work_name);
//        add_event_remark = findViewById(R.id.add_event_remark);
        textViewError = findViewById(R.id.textViewError);

        //edittext的存放--!
//        title= add_event_name.getText().toString();
//        remark = add_event_remark.getText().toString();
        //date picker builder--!
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select day");
        final MaterialDatePicker materialDatePicker = builder.build();
        //---add_event_date---onclicklistener
        add_event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                year1 = calendar.get(Calendar.YEAR);
                month1 = calendar.get(Calendar.MONTH);
                date1 = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(addevent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
//                        test1 = year + "-" + (month+1) + "-" + date;
                        String dateconvertedstr;
                        if(date<10){
                            dateconvertedstr="0"+date;
                        }
                        else
                            dateconvertedstr=String.valueOf(date);


                        test1=dateconvertedstr+" "+MonthList[month]+" "+year;
                        year1 = year;
                        month1 = (month+1);
                        date1 = date;
                        add_event_date.setText(test1);
                        textViewError.setText("");//更新錯誤訊息


                    }
                },year1,month1,date1);




                ScheduleName =add_event_name.getText().toString();

                datePickerDialog.show();
            }
        });


        //date picker positive btn on click listener


                //---color selection--------------
        colorSequence = new CharSequence[]{"red","orange","yellow","teal","green","blue","navy","purple","none"};
        choose_event_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(addevent.this);
                builder.setTitle("選取顏色").setCancelable(true).setSingleChoiceItems(colorSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colornumber = which;
                    }
                }).setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setIcon(R.drawable.ic_baseline_color_lens_24)
                        .setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(colornumber==0){choose_event_color.setBackgroundResource(R.color.red);choose_event_color.setText("red");}
                        else if(colornumber==1){choose_event_color.setBackgroundResource(R.color.orange);choose_event_color.setText("orange");}
                        else if(colornumber==2){choose_event_color.setBackgroundResource(R.color.yellow);choose_event_color.setText("yellow");}
                        else if(colornumber==3){choose_event_color.setBackgroundResource(R.color.teal_200);choose_event_color.setText("teal");}
                        else if(colornumber==4){choose_event_color.setBackgroundResource(R.color.green);choose_event_color.setText("green");}
                        else if(colornumber==5){choose_event_color.setBackgroundResource(R.color.blue);choose_event_color.setText("blue");}
                        else if(colornumber==6){choose_event_color.setBackgroundResource(R.color.navy);choose_event_color.setText("navy");}
                        else if(colornumber==7){choose_event_color.setBackgroundResource(R.color.purple_200);choose_event_color.setText("purple");}
                        else {colornumber = 8;choose_event_color.setBackgroundResource(R.color.light_gray);choose_event_color.setTextColor(Color.rgb(0,0,0));choose_event_color.setText("none");}
                        ScheduleColor =ColorList[colornumber];


                    }

                }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        //------color selection end---------------------------------------------------------------------

        //------notification----------------------------------------------------------------------------
        notificationTimeSequence = new CharSequence[] {"10分鐘","30分鐘","1小時","3小時","不提醒"};
        choose_event_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(addevent.this);
                builder.setTitle("選取時間").setSingleChoiceItems(notificationTimeSequence, 4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //not finish code
                    }
                }).setIcon(R.drawable.ic_baseline_notifications_24)
                        .setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //not finish code
                            }
                        }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            //not finish code
                    }
                });
                builder.show();
            }
        });

        //.



        //----------------------notification end----------------------------------
        //------secure---的跳轉


        add_event_secure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data
                String scheduleTitle =add_event_name.getText().toString();;
                String scheduleDate = add_event_date.getText().toString();
                String scheduleBeginTime = add_event_begin_time.getText().toString();
                String scheduleEndTime = add_event_end_time.getText().toString();
                String scheduleKind = "Schedule";
                String scheduleColor = ScheduleColor;


                if(TextUtils.isEmpty(scheduleTitle)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入行程名稱！");
                    return;
                }

                if (TextUtils.isEmpty(scheduleDate)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇日期！");
                    return;
                }

                if(TextUtils.isEmpty(scheduleBeginTime) || TextUtils.isEmpty(scheduleEndTime)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇時間！");
                    return;
                }

                if(endTimeHour<beginTimeHour){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("時間選擇有誤!");
                    return;
                }

                if(endTimeHour==beginTimeHour&&endTimeMinute<beginTimeMinute){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("時間選擇有誤！");
                    return;
                }

                if(scheduleColor == "" || scheduleColor == "none" || ScheduleColor == null){
                    scheduleColor = "none";
                }

                ScheduleData scheduleData = new ScheduleData(scheduleTitle, scheduleDate,scheduleBeginTime,scheduleEndTime,scheduleKind,scheduleColor);
                UsersRef.document(user).collection("Schedule").document().set(scheduleData);
                textViewError.setTextColor(Color.rgb(81,146,164));
                textViewError.setText("加入成功！");

                db.collection("users").document(user).collection("Schedule")
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
                                            if (idList.size() == 0) {
                                                for (int i = 0; i < arr.length; i += 6) {
                                                    Event_today.eventsList.add(new Event_today(document.getId(), mapData.get(arr[i + 5]).toString(), mapData.get(arr[i + 2]).toString(), mapData.get(arr[i]).toString() + "-" + mapData.get(arr[i + 3]).toString(), mapData.get(arr[i + 4]).toString(), mapData.get(arr[i + 1]).toString()));
                                                    Log.i("sss" , "addeventifSuccessful"+document.getId());
                                                }
                                            }
                                            else {
                                                if (!addevent.idList.contains(document.getId())) {
                                                    for (int i = 0; i < arr.length; i += 6) {
                                                        Event_today.eventsList.add(new Event_today(document.getId(), mapData.get(arr[i + 5]).toString(), mapData.get(arr[i + 2]).toString(), mapData.get(arr[i]).toString() + "-" + mapData.get(arr[i + 3]).toString(), mapData.get(arr[i + 4]).toString(), mapData.get(arr[i + 1]).toString()));
                                                        Log.i("sss", "addeventelseSuccessful"+document.getId());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });

                db.collection("users").document(user).collection("Schedule")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document:task.getResult()){
                                        String id=document.getId();
                                        if (!addevent.idList.contains(document.getId())){
                                            addevent.idList.add(id);
                                            Log.i("sss", 222222+" "+id + " => " + document.getData());
                                        }
                                    }
                                }
                                else{
                                    Log.w("sss", "Error getting documents.", task.getException());

                                }
                            }
                        });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        });

        lEvent = findViewById(R.id.constraintLayoutAddevent);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        add_event_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showKeyBoard(add_event_name);
                //get value from edittext
                String s = add_event_name.getText().toString().trim();
                textViewError.setText("");//更新錯誤訊息
                //check condition
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //when action is equal to action done
                    //hide keyboard
                    hideKeyBoard(add_event_name);
                    //display toast

                    return true;
                }

                return false;
            }
        });

        lEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch is detected");

                int eventType = event.getActionMasked();

                switch (eventType){
                    case MotionEvent.ACTION_DOWN:
                        hideKeyBoard(add_event_name);
                        break;
                }

                return true;
            }
        });

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

    public void popBeginTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectHour, int selectMinute) {
                beginTimeHour = selectHour;
                beginTimeMinute = selectMinute;
                add_event_begin_time.setText(String.format(Locale.getDefault(), "%02d : %02d", beginTimeHour, beginTimeMinute));
                textViewError.setText("");//更新錯誤訊息
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,beginTimeHour,beginTimeMinute,true);
        timePickerDialog.setTitle("選取時間");
        timePickerDialog.show();
    }

    public void popEndTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectHour, int selectMinute) {
                endTimeHour = selectHour;
                endTimeMinute = selectMinute;
                add_event_end_time.setText(String.format(Locale.getDefault(),"%02d : %02d",endTimeHour,endTimeMinute));
                textViewError.setText("");//更新錯誤訊息
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,endTimeHour,endTimeMinute,true);

        timePickerDialog.setTitle("選取時間");
        timePickerDialog.show();

    }

}