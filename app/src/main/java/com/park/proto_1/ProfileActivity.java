package com.park.proto_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    ImageView profileImageV;
    private FirebaseUser user;
    TextView nameT;
    TextView phoneT;
    TextView birthdayT;
    TextView addressT;
    TextView pointT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameT = (TextView)findViewById(R.id.name);
        phoneT = (TextView)findViewById(R.id.phone);
        birthdayT = (TextView)findViewById(R.id.birthday);
        addressT = (TextView)findViewById(R.id.address);
        pointT = (TextView)findViewById(R.id.point);
        profileImageV = findViewById(R.id.profileImage);

        Button logout = (Button)findViewById(R.id.logout_btn);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                mystartActivity(SignUpActivity.class);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavigationView.postDelayed(() -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.main) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else if (itemId == R.id.api) {
                        startActivity(new Intent(getApplicationContext(), Api_main.class));
                    }else if (itemId == R.id.profile) {

                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }else if (itemId == R.id.archive) {
                        startActivity(new Intent(getApplicationContext(), ArchiveActivity.class));
                    }
                    finish();
                }, 100);
                return true;
            };
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        profileUpdate();
    }

    private void profileUpdate() {

        FirebaseFirestore DB = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/profileImage.jpg");
        StorageReference path = storageRef.child("photoUrl");
        if(path == null){
            Toast.makeText(ProfileActivity.this, "프로필 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        }else{
            StorageReference ProfileImage = mountainImagesRef;
            ProfileImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(ProfileActivity.this).load(uri).into(profileImageV);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            DB.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().getData() == null){
                            startToast("회원정보를 입력해주세요.");
                            startActivity(new Intent(getApplicationContext(), MemberInitActivity.class));
                            finish();
                        }else {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Object NM = documentSnapshot.getData().get("name");
                            Object PH = documentSnapshot.getData().get("phone");
                            Object BD = documentSnapshot.getData().get("birthday");
                            Object AD = documentSnapshot.getData().get("address");
                            Object PT = documentSnapshot.getData().get("point");
                            nameT.setText("이름:  " + NM.toString());
                            phoneT.setText("전화번호:  " + PH.toString());
                            birthdayT.setText("생년월일:  " + BD.toString());
                            addressT.setText("주소:  " + AD.toString());
                            pointT.setText("잔여 포인트:  " + PT.toString() + "point");
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"Faild", e);

                }
            });
        }


    }

    private void mystartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);

    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}