package com.park.proto_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WritePostActivity extends BasicActivity{
    private static final String TAG = "WritePostActivity";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        findViewById(R.id.check).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.check:
                    contentsUpdate();
                    break;
            }
        }
    };

    private void contentsUpdate(){
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.contentsEditText)).getText().toString();

        if(title.length()>0 && contents.length()>0){
            user = FirebaseAuth.getInstance().getCurrentUser();
            WriteInfo writeInfo = new WriteInfo(title, contents, user.getUid());
            startToast("게시물이 등록되었습니다.");
            uploader(writeInfo);
        } else{
            startToast("제목, 내용을 입력해주세요.");
        }
    }

    private void uploader(WriteInfo writeInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(writeInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.e(TAG, "ok ID: " + documentReference.getId());
                startToast("게시물이 등록되었습니다.");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Faild", e);
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
