package com.example.presstest2.FirstPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presstest2.FirstPage.Login;
import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpasswordsuccess);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity
    }

    public void back(View view){
        FirebaseAuth.getInstance().signOut(); //log out
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
    //Disable back button
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
