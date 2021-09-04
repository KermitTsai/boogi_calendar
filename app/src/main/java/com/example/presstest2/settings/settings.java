package com.example.presstest2.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.presstest2.FirstPage.FirstPage;
import com.example.presstest2.calendar.MainActivity;
import com.example.presstest2.R;
import com.example.presstest2.control_act.SysApplication;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import static android.content.ContentValues.TAG;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class settings extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageView personal;
    String userEmail;
    TextView _name,_email;
    Button mdelete,mreset,msave,mchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SysApplication.getInstance().addActivity(this);//為了儲存現在有多少activity


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_settings);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setItemIconTintList(null);
        //------set bottomAppBar null to set background(selector)--------------------------
        //        控制功能列
        bottomNavigationView.setSelectedItemId(R.id.miSetting);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.miSetting:
                        return false;

                    case R.id.placeholder:
                        return false;

                }
                return false;
            }
        });


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userEmail = fAuth.getCurrentUser().getEmail();
        FirebaseUser user = fAuth.getCurrentUser();

        _name = findViewById(R.id.profilename);
        _email = findViewById(R.id.profileemail);
        mdelete = findViewById(R.id.bu_delete_schedule);
        mreset = findViewById(R.id.bu_resetall);
        String get_email = user.getEmail();
        String get_name = user.getDisplayName();
        _email.setText(get_email);
        _name.setText(get_name);


        DocumentReference documentReference = fStore.collection("users").document(userEmail);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                _name.setText(documentSnapshot.getString("fName"));
                _email.setText(documentSnapshot.getString("email"));
            }
        });

        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteBatch batch = fStore.batch();

                fStore.collection("users").document(userEmail).collection("test")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        batch.delete(document.getReference());
                                        Toast.makeText(settings.this,"刪除成功",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(settings.this,"刪除失敗",Toast.LENGTH_SHORT).show();
                                }
                                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.w(TAG, "Batch completed.", task.getException());
                                    }
                                });
                            }
                        });
            }
        });
        //------set FAB null to set background(selector)--------------------------
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_settings);
        //to set "add sign"
        floatingActionButton.setImageTintList(null);
        //to set "FAB" ring color
        floatingActionButton.setBackgroundTintList(null);
        //------set FAB null to set background(selector)--------------------------


//unused code google登入的頭貼
//        if(user.getPhotoUrl() != null){
//            String phtoUrl = user.getPhotoUrl().toString();
//            phtoUrl=phtoUrl+"?type=large";
//
//            Picasso.get().load(phtoUrl).placeholder(R.drawable.image_circle)
//                    .error(R.drawable.image_circle).transform(new CircleTransform()).into(avatar);
//        }


//        控制頭貼
        personal=findViewById(R.id.iv_personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity();
            }
        });




    }

    int counter = 0 ;

    @Override
    public void onBackPressed() {
        counter++;
        if(counter == 1){
            super.onBackPressed();
        }
    }

    //控制頭貼
    private void startCropActivity(){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setCropShape(CropImageView.CropShape.OVAL).setFixAspectRatio(true).start(this);
    }
//訪問相簿權限
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK){
             Uri resultUri = result.getUri();
             personal.setImageURI(resultUri);
        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
            Exception error = result.getError();
        }
    }
}


//unused code google登入的頭貼

//    public class CircleTransform implements Transformation {
//        @Override
//        public Bitmap transform(Bitmap source) {
//            int size = Math.min(source.getWidth(), source.getHeight());
//            int x = (source.getWidth() - size);
//            int y = (source.getHeight() - size);
//            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
//            if (squaredBitmap != source) {
//                source.recycle();
//            }
//            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
//            Canvas canvas = new Canvas(bitmap);
//            Paint paint = new Paint();
//            BitmapShader shader = new BitmapShader(squaredBitmap,
//                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
//            paint.setShader(shader);
//            paint.setAntiAlias(true);
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//            squaredBitmap.recycle();
//            return bitmap;
//        }

//        @Override
//        public String key() {
//            return "circle";
//        }
//    }

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
                Toast.makeText(settings.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(getApplicationContext(),FirstPage.class));
        finish();
    }


    public void name(View view){
        Intent intent = new Intent(this, Name.class);
        startActivity(intent);
        finish();

    }

    public void changepassword(View view){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
        finish();
    }
////    禁用返回鍵
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
//        return false;
//    }
}