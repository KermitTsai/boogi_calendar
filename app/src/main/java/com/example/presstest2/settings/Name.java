package com.example.presstest2.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import android.text.TextUtils;

public class Name extends AppCompatActivity {
    private static final String TAG = "Name";

    private EditText mName;
    private Button mFinishButton;
    private static final String key ="fName";
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //    設定頭貼
    FirebaseFirestore fStore_name;
    String userEmail_name;
    TextView _name_name, _email_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity

        userEmail = fAuth.getCurrentUser().getEmail();

        mName = findViewById(R.id.Name);
        mFinishButton = findViewById(R.id.bu_name_send);

        mFinishButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference noteRef = db.collection("users").document(userEmail);
                String name = mName.getText().toString();
                noteRef.update(key,name).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if(TextUtils.isEmpty(name)){
                            mName.setError("不可為空");
                            return;
                        }
                        Toast.makeText(Name.this,"更新完成",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), settings.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Name.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //        設定頭貼
        fStore_name = FirebaseFirestore.getInstance();
        userEmail_name = fAuth.getCurrentUser().getEmail();
        FirebaseUser user_password = fAuth.getCurrentUser();


        _name_name = findViewById(R.id.profilename_name);
        _email_name = findViewById(R.id.profileemail_name);

        String get_email_password = user_password.getEmail();
        String get_name_password = user_password.getDisplayName();
        _email_name.setText(get_email_password);
        _name_name.setText(get_name_password);


        DocumentReference documentReference = fStore_name.collection("users").document(userEmail_name);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                _name_name.setText(documentSnapshot.getString("fName"));
                _email_name.setText(documentSnapshot.getString("email"));
            }
        });

        //以控制返回不會退出程式
        Button back=findViewById(R.id.name_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Name.this,settings.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //if pressed back button one time,back to the previous activity

    int counter = 0;

    @Override
    public void onBackPressed() {
        counter++;
        if(counter == 1){
            super.onBackPressed();
        }
    }
}
