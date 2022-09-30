package com.park.proto_1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MemberInitActivity extends BasicActivity {
    private static final String TAG = "MemberInitActivity";
    private ImageView profileImage;
    private String profilePath;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(onClickListener);

        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.pictureBtn).setOnClickListener(onClickListener);
        findViewById(R.id.gallery).setOnClickListener(onClickListener);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0 : {
                if(resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImage);
                }
                break;
            }
        }
    }

    View.OnClickListener onClickListener = view -> {
        switch (view.getId()){
            case R.id.checkButton:
                profileUpdate();
                break;
            case R.id.profileImage:
                CardView cardView = findViewById(R.id.cardView);
                if(cardView.getVisibility() == View.VISIBLE) {
                    cardView.setVisibility(View.GONE);
                }else{
                    cardView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.pictureBtn:
                mystartActivity(CameraActivity.class);
                break;
            case R.id.gallery:
                mystartActivity(GalleryActivity.class);
                break;


        }
    };

    private void profileUpdate() {
        final String name = ((EditText)findViewById(R.id.name)).getText().toString();
        final String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
        final String birthday = ((EditText)findViewById(R.id.birthday)).getText().toString();
        final String address = ((EditText)findViewById(R.id.postalAddress)).getText().toString();
        int point = 5000;
        if(name.length() > 0 && phone.length() > 9 && birthday.length() > 5 && address.length() > 0) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/profileImage.jpg");

            if(profilePath == null) {
                MemberInfo memberInfo = new MemberInfo(name, phone, birthday, address, point);
                uploader(memberInfo);
            }else{
                try {
                    InputStream stream = new FileInputStream(new File(profilePath));
                    UploadTask uploadTask = mountainImagesRef.putStream(stream);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                MemberInfo memberInfo = new MemberInfo(name, phone, birthday, address, downloadUri.toString(), point);
                                uploader(memberInfo);
                            } else {
                                startToast("회원정보를 전송실패.");
                            }
                        }
                    });
                } catch (FileNotFoundException e){
                    Log.e("로그", "에러 "+e.toString());
                }
            }
        } else {
            startToast("회원정보를 입력해주세요.");
        }
    }

    private void uploader(MemberInfo memberInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(memberInfo)
                .addOnSuccessListener(aVoid -> {
                    startToast("회원정보 등록 완료");
                    mystartActivity(MainActivity.class);
                    finish();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("회원정보 등록 실패");
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void mystartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }


}

