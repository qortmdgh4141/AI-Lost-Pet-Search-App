package com.park.proto_1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Api_main extends AppCompatActivity{
    private static final String TAG = "TestActivity-레트로핏";

    // 어답터
    private ApiRecyclerAdapter adapter;
    // 리사이클러뷰
    private RecyclerView recyclerView;
    // 진행바
    ProgressDialog progressDoalog;
    //private List<ApiData> ApiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);

        // 진행중바
        progressDoalog = new ProgressDialog(Api_main.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        // 레트로핏 인스턴스 생성을 해줍니다.
        // enqueue로 비동기 통신을 싱행합니다.
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);
        Call<List<AbdmAnimalProtect>> call = service.getAllData();
        Log.i("제발나와", service.getAllData().toString());

        //통신완료후 이벤트 처리를 위한 콜백 리스너 등록
        call.enqueue(new Callback<List<AbdmAnimalProtect>>() {
            // 정상으로 통신 성공시
            @Override
            public void onResponse(Call<List<AbdmAnimalProtect>> call, Response<List<AbdmAnimalProtect>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
                Log.i("wefkhwehflkwwwww", response.body().toString());
                adapter.notifyDataSetChanged();
            }
            // 통신 실패시(예외발생, 인터넷끊김 등의 이유)
            @Override
            public void onFailure(Call<List<AbdmAnimalProtect>> call, Throwable t) {
                progressDoalog.dismiss();
                Log.i("dslfkjhsdlkifhjsdlkf", call.toString());
                Toast.makeText(Api_main.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 리사이클러뷰
    private void generateDataList(List<AbdmAnimalProtect> ApiList) {
        recyclerView = findViewById(R.id.Apirecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ApiRecyclerAdapter(this, ApiList);
        recyclerView.setAdapter(adapter);

    }

}
