package com.example.presstest2.event.event_today;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.presstest2.R;
import com.example.presstest2.calendar.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class add_course extends AppCompatActivity {

    //---選取時段的宣告---
    private Button selectBeginTime,selectEndTime;
    private ImageButton pickTimeInfo;

    //---chooseColor dialog---
    private TextView selectColor;
    private CharSequence[] colorSequence;
    private TextView choose_event_color;
    private int colornumber;
    //---notification dialog---
    private MaterialButton selectNotificationTime;
    private CharSequence[] notificationTimeSequence;

    private EditText editTextCourseName;
    private CharSequence[] courseTimeSequence;
    private CharSequence[] courseDaySequence;
    private TextView choose_course_time;
    private TextView choose_course_day;
    private int courseTimeNumberS;
    private int courseTimeNumberE;
    int courseTimeNumberSResult;
    int courseTimeNumberEResult;
    private int courseDayNumber;
    private TextView textViewError;

    private Button add_course_secure;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth;
    private String user;

    private ConstraintLayout lCourse;


    BottomNavigationView navigationView ;


    //tag test
    private int tag01;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser().getEmail();

        //---選取時段的find---
        selectBeginTime = findViewById(R.id.add_course_pickbegintime);
        selectEndTime = findViewById(R.id.add_course_pickendtime);
        editTextCourseName = findViewById(R.id.add_work_name);
        choose_course_day = findViewById(R.id.add_course_day);
        textViewError = findViewById(R.id.textViewError_course);
//        choose_event_color = findViewById(R.id.add_course_chooseColor);

        navigationView=findViewById(R.id.kind_navigation_course);
        navigationView.setBackgroundTintList(null);

        navigationView.setSelectedItemId(R.id.navi_course);
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
                        return false;

                    case R.id.navi_work:
                        startActivity(new Intent(getApplicationContext(), add_work.class));
                        overridePendingTransition(0,0);
                        finish();
                        return false;
                }
                return false;
            }
        });
        add_course_secure = findViewById(R.id.add_event_secure);

        courseTimeSequence = new CharSequence[] {"第一節","第二節","第三節","第四節","第五節","第六節","第七節","第八節"
                ,"第九節","第十節","第十一節","第十二節","第十三節","第十四節"};

        selectBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_course.this);
                builder.setTitle("選取開始節次").setSingleChoiceItems(courseTimeSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseTimeNumberS = which;
                    }
                }).setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setIcon(R.drawable.ic_baseline_access_time_filled_24)
                        .setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(courseTimeNumberS==0){selectBeginTime.setText("第一節");}
                        else if(courseTimeNumberS==1){selectBeginTime.setText("第二節");}
                        else if(courseTimeNumberS==2){selectBeginTime.setText("第三節");}
                        else if(courseTimeNumberS==3){selectBeginTime.setText("第四節");}
                        else if(courseTimeNumberS==4){selectBeginTime.setText("第五節");}
                        else if(courseTimeNumberS==5){selectBeginTime.setText("第六節");}
                        else if(courseTimeNumberS==6){selectBeginTime.setText("第七節");}
                        else if(courseTimeNumberS==7){selectBeginTime.setText("第八節");}
                        else if(courseTimeNumberS==8){selectBeginTime.setText("第九節");}
                        else if(courseTimeNumberS==9){selectBeginTime.setText("第十節");}
                        else if(courseTimeNumberS==10){selectBeginTime.setText("第十一節");}
                        else if(courseTimeNumberS==11){selectBeginTime.setText("第十二節");}
                        else if(courseTimeNumberS==12){selectBeginTime.setText("第十三節");}
                        else if(courseTimeNumberS==13){selectBeginTime.setText("第十四節");}
                        textViewError.setText("");
                    }
                }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //not finish code
                    }
                });
                courseTimeNumberSResult = courseTimeNumberS;
                builder.show();
            }
        });

        selectEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_course.this);
                builder.setTitle("選取結束節次").setSingleChoiceItems(courseTimeSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseTimeNumberE = which;
                    }
                }).setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setIcon(R.drawable.ic_baseline_access_time_24)
                        .setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(courseTimeNumberE==0){selectEndTime.setText("第一節");}
                        else if(courseTimeNumberE==1){selectEndTime.setText("第二節");}
                        else if(courseTimeNumberE==2){selectEndTime.setText("第三節");}
                        else if(courseTimeNumberE==3){selectEndTime.setText("第四節");}
                        else if(courseTimeNumberE==4){selectEndTime.setText("第五節");}
                        else if(courseTimeNumberE==5){selectEndTime.setText("第六節");}
                        else if(courseTimeNumberE==6){selectEndTime.setText("第七節");}
                        else if(courseTimeNumberE==7){selectEndTime.setText("第八節");}
                        else if(courseTimeNumberE==8){selectEndTime.setText("第九節");}
                        else if(courseTimeNumberE==9){selectEndTime.setText("第十節");}
                        else if(courseTimeNumberE==10){selectEndTime.setText("第十一節");}
                        else if(courseTimeNumberE==11){selectEndTime.setText("第十二節");}
                        else if(courseTimeNumberE==12){selectEndTime.setText("第十三節");}
                        else if(courseTimeNumberS==13){selectEndTime.setText("第十四節");}
                        textViewError.setText("");
                    }
                }).setNegativeButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //not finish code
                    }
                });
                courseTimeNumberEResult = courseTimeNumberE;
                builder.show();
            }
        });

        courseDaySequence = new CharSequence[] {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        choose_course_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_course.this);
                builder.setTitle("選取日").setSingleChoiceItems(courseDaySequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseDayNumber = which;
                    }
                }).setBackground(getResources().getDrawable(R.drawable.rounded))
                        .setIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(courseDayNumber==0){choose_course_day.setText("星期一");}
                        else if(courseDayNumber==1){choose_course_day.setText("星期二");}
                        else if(courseDayNumber==2){choose_course_day.setText("星期三");}
                        else if(courseDayNumber==3){choose_course_day.setText("星期四");}
                        else if(courseDayNumber==4){choose_course_day.setText("星期五");}
                        else if(courseDayNumber==5){choose_course_day.setText("星期六");}
                        else {choose_course_day.setText("星期日");}
                        textViewError.setText("");
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




        //---color select 的 find ---
//        selectColor =findViewById(R.id.add_course_chooseColor);
        colorSequence = new CharSequence[]{"red","orange","yellow","teal","green","blue","navy","light purple","none"};

//        selectColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(MainActivity.this);
//                builder.setTitle("選擇顏色");
//                builder.setSingleChoiceItems(colorSequence, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        colornumber = which;
//                    }
//                });
//                builder.setIcon(R.drawable.ic_color_lens_24);
//                builder.setBackground(getResources().getDrawable(R.drawable.dialog_rounded_bg));
//                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
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
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //not finish code
//                    }
//                });
//
//                builder.show();
//            }
//        });

        //---notification select 的 find ----
        selectNotificationTime = findViewById(R.id.add_course_notification);
        notificationTimeSequence = new CharSequence[]{"10分鐘","30分鐘","1小時","3小時","不提醒"};

        selectNotificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(add_course.this);
                builder.setTitle("選取時間");
                builder.setSingleChoiceItems(notificationTimeSequence, 4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //not finish code
                    }
                });
                builder.setIcon(R.drawable.ic_notifications_24);
                builder.setBackground(getResources().getDrawable(R.drawable.rounded));

                builder.setPositiveButton(Html.fromHtml("<font color='#53D3C3'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // not finish code
                    }
                });
                builder.setNegativeButton(Html.fromHtml("<font color='#53D3C3'>取消</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // not finish code
                    }
                });
                builder.show();

            }
        });

        add_course_secure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextCourseName.getText().toString();
                String courseStartTime = selectBeginTime.getText().toString();
                String courseEndTime = selectEndTime.getText().toString();
                String courseDay  = choose_course_day.getText().toString();
                String courseStartTimeRes = "";
                String courseEndTimeRes = "";
                if(TextUtils.isEmpty(courseName)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請輸入課程名稱！");

                    return;
                }
                if(TextUtils.isEmpty(courseDay)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇星期！");
                    return;
                }
                if(TextUtils.isEmpty(courseStartTime)||TextUtils.isEmpty(courseEndTime)){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("請選擇節次！");
                    return;
                }
                if(courseTimeNumberS + 1 > courseTimeNumberE + 1){
                    textViewError.setTextColor(Color.rgb(251,139,36));
                    textViewError.setText("節次選擇有誤！");
                    return;
                }


                else{
                    Map<String,Object> data = new HashMap<>();
                    Map<String,String> courseTimeArray = new HashMap<>();
                    courseTimeArray.put("第一節","08:00~09:00");
                    courseTimeArray.put("第二節","09:00~10:00");
                    courseTimeArray.put("第三節","10:00~11:00");
                    courseTimeArray.put("第四節","11:00~12:00");
                    courseTimeArray.put("第五節","12:00~13:00");
                    courseTimeArray.put("第六節","13:00~14:00");
                    courseTimeArray.put("第七節","14:00~15:00");
                    courseTimeArray.put("第八節","15:00~16:00");
                    courseTimeArray.put("第九節","16:00~17:00");
                    courseTimeArray.put("第十節","17:00~18:00");
                    courseTimeArray.put("第十一節","18:00~19:00");
                    courseTimeArray.put("第十二節","19:00~20:00");
                    courseTimeArray.put("第十三節","20:00~21:00");
                    courseTimeArray.put("第十四節","21:00~22:00");

                    for(String ss : courseTimeArray.keySet()){
                        if(courseStartTime == ss){
                            courseStartTimeRes = courseTimeArray.get(ss);
                        }
                    }
                    for(String ss : courseTimeArray.keySet()){
                        if(courseEndTime == ss){
                            courseEndTimeRes = courseTimeArray.get(ss);
                        }
                    }
                    data.put(courseStartTimeRes,courseName);
                    data.put(courseEndTimeRes,courseName);

                    if(courseTimeNumberE > courseTimeNumberS){
                        for(int i = courseTimeNumberS+1; i < courseTimeNumberE; i++){
                            for(String ss : courseTimeArray.keySet()){
                                if(courseTimeSequence[i] == ss){
                                    data.put(courseTimeArray.get(ss),courseName);
                                }
                            }
                        }
                    }


                    if(courseDay == "星期一"){
                        db.collection("users").document(user).collection("Course").document("1").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期二"){
                        db.collection("users").document(user).collection("Course").document("2").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期三"){
                        db.collection("users").document(user).collection("Course").document("3").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期四"){
                        db.collection("users").document(user).collection("Course").document("4").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期五"){
                        db.collection("users").document(user).collection("Course").document("5").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期六"){
                        db.collection("users").document(user).collection("Course").document("6").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }
                    else if(courseDay == "星期日")
                    {
                        db.collection("users").document(user).collection("Course").document("7").set(data, SetOptions.merge());
                        textViewError.setTextColor(Color.rgb(97,155,138));
                        textViewError.setText("加入成功！");
                    }

                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        });

        lCourse = findViewById(R.id.constraintLayoutAddcourse);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        editTextCourseName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showKeyBoard(editTextCourseName);
                //get value from edittext
                String s = editTextCourseName.getText().toString().trim();
                textViewError.setText("");//更新錯誤訊息
                //check condition
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //when action is equal to action done
                    //hide keyboard
                    hideKeyBoard(editTextCourseName);
                    //display toast

                    return true;
                }

                return false;
            }
        });

        lCourse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch is detected");

                int eventType = event.getActionMasked();

                switch (eventType){
                    case MotionEvent.ACTION_DOWN:
                        hideKeyBoard(editTextCourseName);
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
}