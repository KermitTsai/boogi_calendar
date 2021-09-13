package com.example.presstest2.merge;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.presstest2.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MergeActivity extends MergeMainActivity {

    TextView resultTextView;
    Button mergeButton;
    //班组
    //班次
    String[] classArr = {"早班","中班","晚班"};
    String[] dayArr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    String result="";

    public CharSequence[] prioritySequence;
    public TextView choose_priority;
    public int priorityNumber;

    public CharSequence[] priorityTimeSequence;
    public TextView choose_TimePriority;
    public int priorityTimeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);

        choose_priority = findViewById(R.id.choose_priority);
        choose_TimePriority = findViewById(R.id.choose_timePriority);
        mergeButton = findViewById(R.id.MergeBtn);

        resultTextView = findViewById(R.id.resultView);

        prioritySequence = new CharSequence[] {"早班","午班","晚班","早班和午班","午班和晚班","三時段"};
        choose_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MergeActivity.this);
                builder.setTitle("選取日").setSingleChoiceItems(prioritySequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choose_priority.setText(prioritySequence[which]);
                    }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        priorityTimeSequence = new CharSequence[] {"一週兩天班","一週三天班","一週四天班"};
        choose_TimePriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MergeActivity.this);
                builder.setTitle("選取日").setSingleChoiceItems(priorityTimeSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choose_TimePriority.setText(priorityTimeSequence[which]);
                    }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        mergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    public void setdata(){
        int num = getNumber();
        int dayNum = getDayNumber();
        String choosePri = choose_priority.getText().toString();
        String chooseTimePri = choose_TimePriority.getText().toString();

        if(chooseTimePri == "一週四天班"){ //一週四天
            if(choosePri == "三時段"){   //三時段
                for (int classNum = 0; classNum < 20; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (night.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num=0;
                    }
                }
            }
            else if(choosePri == "早班"){//早班
                for (int classNum = 0; classNum < 20; classNum++) {
                    result += "早班: \n";
                    for (int i = num; i < morning.size(); i++){
                        if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!morning.get(j).equals("---------------------")){
                                    result += morning.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (morning.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!morning.get(j).equals("---------------------")){
                                    result += morning.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 1;
                    if(dayNum >= 6 || dayNum >=5){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "午班"){ //午班
                for (int classNum = 0; classNum < 31; classNum++) {
                     //午班
                    result += "午班: \n";
                    for (int i = num; i < noon.size(); i++){
                        if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (noon.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 1;
                    if(dayNum >= 6 || dayNum >=5){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "晚班"){
                for (int classNum = 0; classNum < 20; classNum++){
                    result += "晚班: \n";
                    for (int i = num; i < night.size(); i++){
                        if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += night.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (night.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!night.get(j).equals("---------------------")){
                                    result += night.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 1;
                    if(dayNum >= 6 || dayNum >=5) {
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "早班和午班"){
                for (int classNum = 0; classNum < 20; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num = 0;
                    }
                    else{
                        num = 0;
                    }
                }
            }
            else if(choosePri == "午班和晚班"){
                for (int classNum = 0; classNum < 20; classNum++) {
                    if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (night.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 1;
                        if(dayNum >= 6 || dayNum >=5){
                            dayNum = getDaySmallNumber();
                        }
                        num = 1;
                    }
                    else{
                        num = 1;
                    }
                }
            }
        }
        else if(chooseTimePri == "一週兩天班"){
            if(choosePri == "三時段"){   //三時段
                for (int classNum = num; classNum < 10; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum +=4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (night.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num=0;
                    }
                }
            }
            else if(choosePri == "早班"){
                for (int classNum = 0; classNum < 10; classNum++) {
                    result += "早班: \n";
                    for (int i = num; i < morning.size(); i++){
                        if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!morning.get(j).equals("---------------------")){
                                    result += morning.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (morning.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!morning.get(j).equals("---------------------")){
                                    result += morning.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 4;
                    if(dayNum >= 7){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "午班"){
                for (int classNum = 0; classNum < 10; classNum++) {
                    //午班
                    result += "午班: \n";
                    for (int i = num; i < noon.size(); i++){
                        if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (noon.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 4;
                    if(dayNum >= 7){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "晚班"){
                for (int classNum = 0; classNum < 10; classNum++){
                    result += "晚班: \n";
                    for (int i = num; i < night.size(); i++){
                        if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!night.get(j).equals("---------------------")){
                                    result += night.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }else if (night.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!night.get(j).equals("---------------------")){
                                    result += night.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 4;
                    if(dayNum >= 7) {
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "早班和午班"){
                for (int classNum = 0; classNum < 10; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum]) && morning.get(i+1).equals(dayArr[dayNum+1]) && i+1<=morning.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num = 0;
                    }
                    else{
                        num = 0;
                    }
                }
            }
            else if(choosePri == "午班和晚班"){
                for (int classNum = 0; classNum < 10; classNum++) {
                    if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1]) && i+1<=noon.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }else if (night.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 4;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num = 1;
                    }
                    else{
                        num = 1;
                    }
                }
            }
        }
        else if(chooseTimePri == "一週三天班"){
            if(choosePri == "三時段"){   //三時段
                for (int classNum = 0; classNum < 15; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum]) && night.get(i+1).equals(dayArr[dayNum+1]) && i+1<=night.size()){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num=0;
                    }
                }
            }
            else if(choosePri == "早班"){
                for (int classNum = 0; classNum < 15; classNum++) {
                    result += "早班: \n";
                    for (int i = num; i < morning.size(); i++){
                        if(morning.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!morning.get(j).equals("---------------------")){
                                    result += morning.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 2;
                    if(dayNum >= 7){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "午班"){
                for (int classNum = 0; classNum < 15; classNum++) {
                    //午班
                    result += "午班: \n";
                    for (int i = 0; i < noon.size(); i++){
                        if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1])){
                            for(int j = i+1 ;j < i+4 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                        else if (noon.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!noon.get(j).equals("---------------------")){
                                    result += noon.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 2;
                    if(dayNum >= 7){
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "晚班"){
                for (int classNum = 0; classNum < 15; classNum++){
                    result += "晚班: \n";
                    for (int i = num; i < night.size(); i++){
                        if(night.get(i).equals(dayArr[dayNum])){
                            for(int j = i ;j < i+3 ;j++){
                                if(!night.get(j).equals("---------------------")){
                                    result += night.get(j) + "\n";
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    result += "---------------------\n";
                    dayNum += 2;
                    if(dayNum >= 7) {
                        dayNum = getDaySmallNumber();
                    }
                }
            }
            else if(choosePri == "早班和午班"){
                for (int classNum = 0; classNum < 15; classNum++) {
                    if(num==0) { //早班
                        result += "早班: \n";
                        for (int i = getNumber(); i < morning.size(); i++){
                            if(morning.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!morning.get(j).equals("---------------------")){
                                        result += morning.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = 0; i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1])){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                            else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num = 0;
                    }
                    else{
                        num = 0;
                    }
                }
            }
            else if(choosePri == "午班和晚班"){
                for (int classNum = 0; classNum < 15; classNum++) {
                    if (num==1) { //午班
                        result += "午班: \n";
                        for (int i = getNumber(); i < noon.size(); i++){
                            if(noon.get(i).equals(dayArr[dayNum]) && noon.get(i+1).equals(dayArr[dayNum+1])){
                                for(int j = i+1 ;j < i+4 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                            else if (noon.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!noon.get(j).equals("---------------------")){
                                        result += noon.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num+=1;
                    }
                    else if(num==2) { //晚班
                        result += "晚班: \n";
                        for (int i = getNumber(); i < night.size(); i++){
                            if(night.get(i).equals(dayArr[dayNum])){
                                for(int j = i ;j < i+3 ;j++){
                                    if(!night.get(j).equals("---------------------")){
                                        result += night.get(j) + "\n";
                                    }else{
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        result += "---------------------\n";
                        dayNum += 2;
                        if(dayNum >= 7){
                            dayNum = getDaySmallNumber();
                        }
                        num = 1;
                    }
                    else{
                        num = 1;
                    }
                }
            }
        }
        resultTextView.setText(result);
        result = "";
    }

    private int getNumber() {
        int num;
        num = (int) ((Math.random() * 2));
        return num;
    }

    private int getDayNumber() {
        int num;
        num = (int) ((Math.random() * 6));
        return num;
    }

    private int getDaySmallNumber() {
        int num;
        num = (int) ((Math.random() * 1)+1);
        return num;
    }
    private int getDayBigNumber() {
        int num;
        num = (int) ((Math.random() * 3));
        return num;
    }
}