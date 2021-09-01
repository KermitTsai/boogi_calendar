package com.example.presstest2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ChangePassword extends AppCompatActivity {

    EditText mEmail;
    EditText mPassWord;
    Button mGetButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView _name_password, _email_password;


//    設定頭貼
    FirebaseFirestore fStore_password;
    String userEmail_password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        mEmail = findViewById(R.id.Email);
        mPassWord = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mGetButton = findViewById(R.id.bu_password_send);

        mGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("需填入電子郵件.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassWord.setError("需填入密碼");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                 //authenticate the user

                String mail = mEmail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChangePassword.this,"請重新登入",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),PassEmail.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePassword.this, "錯誤！重置連結並未傳送。" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });


//        設定頭貼
        fStore_password = FirebaseFirestore.getInstance();
        userEmail_password = fAuth.getCurrentUser().getEmail();
        FirebaseUser user_password = fAuth.getCurrentUser();


        _name_password = findViewById(R.id.profilename_password);
        _email_password = findViewById(R.id.profileemail_password);

        String get_email_password = user_password.getEmail();
        String get_name_password = user_password.getDisplayName();
        _email_password.setText(get_email_password);
        _name_password.setText(get_name_password);


        DocumentReference documentReference = fStore_password.collection("users").document(userEmail_password);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                _name_password.setText(documentSnapshot.getString("fName"));
                _email_password.setText(documentSnapshot.getString("email"));
            }
        });
    }

    public void back(View view){
        Intent intent = new Intent(this,settings.class);
        startActivity(intent);
    }

}
