package com.example.presstest2.merge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presstest2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class MergeMainActivity extends AppCompatActivity {
    TextView timeTextview,timeTextview2;
    Button mergeButton;
    String data = "";
    String[] dutyArr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

    public static ArrayList <String> morning = new ArrayList<>(); //早班陣列
    public static ArrayList <String> noon = new ArrayList<>(); //午班陣列
    public static ArrayList <String> night = new ArrayList<>(); //晚班陣列

    private ArrayList<String> dayResult;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference userRef = db.collection("users");
    private CollectionReference documentReference = db.collection("users").document("ryanhsu369@gmail.com").collection("Course");
    public String usersId;
    ArrayList<String> id;
    ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_main);

        timeTextview = findViewById(R.id.resultView);
        mergeButton = findViewById(R.id.MergeBtn);

        dayResult = new ArrayList<>();


        mergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MergeActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNullcourse();
    }

    public void getNullcourse(){
//        userRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    Note note = documentSnapshot.toObject(Note.class);
//                    id.add(documentSnapshot.getId());
//                    name.add(note.getfName());
//                }
//            }
//        });

//        for(int i = 0; i < id.size(); i++){
            db.collection("users").document("ryanhsu369@gmail.com").collection("Course").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() { //
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Map<String, Object> map = documentSnapshot.getData();
                        Set set = map.keySet();
                        Object[] arr = set.toArray();
                        Arrays.sort(arr);
                        String day= map.get(arr[0]).toString();
                        data += "------------"+ day +"空閑時段------------\n";
                        morning.add(day);
                        noon.add(day);
                        night.add(day);
                        for(int i = 1; i <= 14; i++){
                            if(map.get(arr[i]) == ""){
                                if(i>=1 && i<=4){ //判斷是否早班
                                    if(map.get(arr[i]) == map.get(arr[i+1]) && i+1 <= 4){  //防止第三堂有課,第四堂空堂且第五堂空堂而加入陣列
                                        morning.add(arr[i].toString());
                                        Log.d("早班", day + " " + arr[i] + "\n");
                                    }
                                    else if(map.get(arr[i]) == map.get(arr[i+1]) && map.get(arr[i+1]) == map.get(arr[i+2]) && i+1 <= 4){ //
                                        morning.add(arr[i].toString());
                                        Log.d("早班", day + " " + arr[i] + "\n");
                                    }
                                    else if(map.get(arr[i]) == map.get(arr[i-1])){
                                        morning.add(arr[i].toString());
                                        morning.add("---------------------");
                                        Log.d("早班", day + " " + arr[i] + "\n");
                                    }
                                }
                                if(i>=5 && i<=10){ //判斷是否午班
                                    if (map.get(arr[i]) == map.get(arr[i+1]) && i+1 <= 10){
                                        noon.add(arr[i].toString());
                                        Log.d("中班", day + " " + arr[i] + "\n");
                                    }
                                    else if (map.get(arr[i]) == map.get(arr[i+1]) && map.get(arr[i+1]) == map.get(arr[i+2]) && i+1 <= 10) {
                                        noon.add(arr[i].toString());
                                        Log.d("中班", day + " " + arr[i] + "\n");
                                    }
                                    else if (map.get(arr[i]) == map.get(arr[i-1]) && i-1 >=5){
                                        noon.add(arr[i].toString());
                                        noon.add("---------------------");
                                        Log.d("中班", day + " " + arr[i] + "\n");
                                    }
                                }
                                if(i>=11 && i<14){ //判斷是否晚班
                                    if (map.get(arr[i]) == map.get(arr[i+1]) && i+1 <= 14) { //
                                        night.add(arr[i].toString());
                                        Log.d("晚班", day + " " + arr[i] + "\n");
                                    }
                                    else if (map.get(arr[i]) == map.get(arr[i+1]) && map.get(arr[i+1]) == map.get(arr[i+2]) && i+1 <= 14){
                                        night.add(arr[i+1].toString());
                                        Log.d("晚班", day + " " + arr[i] + "\n");
                                    }
                                    else if (map.get(arr[i]) == map.get(arr[i-1]) && i <= 14){
                                        night.add(arr[i].toString());
                                        night.add("---------------------");
                                        Log.d("晚班", day + " " + arr[i] + "\n");
                                        break;
                                    }
                                }
                                if(i == 14){ //判斷是否晚班
                                    night.add(arr[i].toString());
                                    night.add("---------------------");
                                    Log.d("晚班", day + " " + arr[i] + "\n");
                                }

                                data += arr[i]+"\n";
                            }
                        }
                    }
                    timeTextview.setText(data);
                    data = "";
                    Log.i("morning", morning.toString());
                    Log.i("noon", noon.toString());
                    Log.i("night",night.toString());
                }
            });
//        }
    }
}