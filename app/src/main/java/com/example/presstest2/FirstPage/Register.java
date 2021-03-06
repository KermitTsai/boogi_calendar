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

import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mConfirmPassword;
    Button mRegisterButton;
    TextView mLoginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userId;
    ConstraintLayout lRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        mFullName = findViewById(R.id.RegisterfullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.RegisterPassword);
        mRegisterButton = findViewById(R.id.registerbutton);
        mLoginButton = findViewById(R.id.Registercreatetext);
        mConfirmPassword = findViewById(R.id.RegisterConfirmPassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        lRegister = findViewById(R.id.constraintLayoutRegister);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Verify.class));
            finish();
        }


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String confirmpassword = mConfirmPassword.getText().toString().trim();

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

                if(!password.equals(confirmpassword)){
                    mPassword.setError("密碼不一致");
                    mConfirmPassword.setError("密碼不一致");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Register.this, "驗證電子郵件已傳送。", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: 電子郵件未傳送。" + e.getMessage());
                                }
                            });

                            Toast.makeText(Register.this,"帳號建立。",Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(email);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess: user Profile is created for "+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: "+ e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Verify.class));

                        }else{
                            Toast.makeText(Register.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        //以控制返回不會退出程式
        Button back=findViewById(R.id.register_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, FirstPage.class);
                startActivity(intent);
                finish();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        lRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch is detected");

                int eventType = event.getActionMasked();

                switch (eventType){
                    case MotionEvent.ACTION_DOWN:
                        hideKeyBoard(mFullName);
                        hideKeyBoard(mEmail);
                        hideKeyBoard(mPassword);
                        hideKeyBoard(mConfirmPassword);
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
