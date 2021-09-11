package com.example.presstest2.FirstPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.presstest2.calendar.MainActivity;
import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginButton;
    TextView mCreateButton, forgetTextLink;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    ConstraintLayout lLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.LoginPassword);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mLoginButton = findViewById(R.id.loginbutton);
        mCreateButton = findViewById(R.id.Logincreatetext);
        forgetTextLink = findViewById((R.id.LoginforgetPassword));
        lLogin = findViewById(R.id.constraintLayoutLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("需填入電子郵件.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("需填入密碼.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("密碼需大於等於6個字元");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"登入成功。",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Login.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

        forgetTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
                finish();
            }
        });
//以控制返回不會退出程式
        Button back=findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, FirstPage.class);
                startActivity(intent);
                finish();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        lLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch is detected");

                int eventType = event.getActionMasked();

                switch (eventType){
                    case MotionEvent.ACTION_DOWN:
                        hideKeyBoard(mPassword);
                        hideKeyBoard(mEmail);
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
}