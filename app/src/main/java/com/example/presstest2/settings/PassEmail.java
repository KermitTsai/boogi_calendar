package com.example.presstest2.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presstest2.FirstPage.FirstPage;
import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class PassEmail extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passemail);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); //log out
        GoogleSignIn.getClient(this,new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(view.getContext(), FirstPage.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PassEmail.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(getApplicationContext(),FirstPage.class));
        finish();
    }

    //Disable back button
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}

