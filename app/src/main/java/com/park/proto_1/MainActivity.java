package com.park.proto_1;

import static com.park.proto_1.Util.isStorageUrl;
import static com.park.proto_1.Util.showToast;
import static com.park.proto_1.Util.storageUrlToName;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends BasicActivity {
    private static final String TAG = "MainActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageRef;
    private MainAdapter mainAdapter;
    private ArrayList<PostInfo> postList;
    private int successCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네비게이션바 이벤트 HT
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.main);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavigationView.postDelayed(() -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.main) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if (itemId == R.id.api) {
                        startActivity(new Intent(getApplicationContext(), Api_main.class));
                    }else if (itemId == R.id.profile) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }else if (itemId == R.id.archive) {
                        startActivity(new Intent(getApplicationContext(), ArchiveActivity.class));
                    }
                    finish();
                }, 100);
                return true;
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if(firebaseUser == null) {
            mystartActivity(SignUpActivity.class);
        }else if(firebaseUser.getMetadata().toString() == null){
            mystartActivity(MemberInitActivity.class);
        }
        else{
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                mystartActivity(MemberInitActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        postList = new ArrayList<>();
        mainAdapter = new MainAdapter(MainActivity.this, postList);
        mainAdapter.setOnPostListener(onPostListener);

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);

        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(mainAdapter);

        FloatingActionButton floatingActionButton;
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.bringToFront();
        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);


        ImageButton mapBtn = (ImageButton) findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), GPSMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        postsUpdate();
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            final String id = postList.get(position).getId();
            ArrayList<String> contentsList = postList.get(position).getContents();
            for(int i=0; i<contentsList.size(); i++){
                String contents = contentsList.get(i);
                if(isStorageUrl(contents)) {
                    successCount++;
                    StorageReference desertRef = storageRef.child("posts/" + id + "/" + storageUrlToName(contents));
                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            successCount--;
                            storeUploader(id);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToast(MainActivity.this, "에러발생");
                        }
                    });
                }
            }
            storeUploader(id);
        }

        @Override
        public void onModify(int position) {
            mystartActivity(WritePostActivity.class, postList.get(position));

        }
    };





    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.logoutButton:
//                    FirebaseAuth.getInstance().signOut();
//                    mystartActivity(SignUpActivity.class);
//                    break;
                case R.id.floatingActionButton:
                    mystartActivity(WritePostActivity.class);

                    break;
            }
        }
    };

    private void postsUpdate() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            postList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new PostInfo(
                                    document.getData().get("title").toString(),
                                    (ArrayList<String>) document.getData().get("contents"),
                                    document.getData().get("publisher").toString(),
                                    new Date(document.getDate("createdAt").getTime()),
                                    document.getId()));
                            }
                            mainAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        }
    }

    private void storeUploader(String id) {
        if (successCount == 0) {
            firebaseFirestore.collection("posts").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showToast(MainActivity.this, "게시글을 삭제하였습니다.");
                        postsUpdate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast(MainActivity.this, "게시글을 삭제하지 못하였습니다.");
                    }
                });
        }
    }

    private void mystartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("postInfo", postInfo);
        startActivity(intent);
    }

    private void mystartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}