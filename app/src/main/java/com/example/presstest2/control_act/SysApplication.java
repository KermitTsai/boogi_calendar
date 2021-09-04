package com.example.presstest2.control_act;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;

//一個類 用來結束所有後臺activity

public class SysApplication extends Application{
    //運用list來儲存們每一個activity是關鍵
    private List<Activity> mList = new LinkedList<Activity>();
    //為了實現每次使用該類時不建立新的物件而建立的靜態物件
    private static SysApplication instance;
    //構造方法
    private SysApplication(){}
    //例項化一次
    public synchronized static SysApplication getInstance(){
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }
    //關閉每一個list內的activity
    public void exit() {
        try {
            for (Activity activity:mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    //殺程式
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
