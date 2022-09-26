package com.park.proto_1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Api_main extends AppCompatActivity{
    private String address = "https://openapi.gg.go.kr/AbdmAnimalProtect?KEY=25af84057280489d90e4a25dd9138611&pIndex=1&Type=json&pSize=50&SIGUN_CD=41590&SPECIES_NM=[개]";


    private Button Hasung_btn;
    private RecyclerView recyclerview;
    private ApiRecyclerAdapter adapter = new ApiRecyclerAdapter();    // adapter 생성

    private ArrayList<ApiData> Apilist = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);

        Hasung_btn = findViewById(R.id.Hasung_btn);
        recyclerview = findViewById(R.id.recyclerview);

        // RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        // recyclerview에 adapter 적용
        recyclerview.setAdapter(adapter);


        // 데이터 불러오기 버튼 클릭
        Hasung_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String urlAddress = address;

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlAddress);

                            InputStream is = url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            StringBuffer buffer = new StringBuffer();
                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }
                            String jsonData = buffer.toString();

                            // jsonData를 먼저 JSONObject 형태로 바꾼다.
                            JSONObject obj = new JSONObject(jsonData);

                            // obj의 "boxOfficeResult"의 JSONObject를 추출
                            String AbdmAnimalProtect = obj.getString("AbdmAnimalProtect");
                            Log.i("제발아러자ㅓㅈ아러", AbdmAnimalProtect);
                            //JSONObject AbdmAnimalProtect = (JSONObject)obj.optJSONObject("SPECIES_NM");

                            // boxOfficeResult의 JSONObject에서 "dailyBoxOfficeList"의 JSONArray 추출

                            JSONArray Info = new JSONArray(AbdmAnimalProtect);
                            Log.i("제발아러자ㅓㅈ아러", Info.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        // dailyBoxOfficeList의 length만큼 for문 반복
                                        for (int i = 0; i < Info.length(); i++) {
                                            // dailyBoxOfficeList를 각 JSONObject 형태로 객체를 생성한다.
                                            JSONObject temp = Info.getJSONObject(i);
                                            String SPECIES_NM = temp.optString("SIGUN_NM");
                                            // list의 json 값들을 넣는다.
                                            Apilist.add(new ApiData(SPECIES_NM));
                                        }
                                        // adapter에 적용
                                        adapter.setmovieList(Apilist);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }
}
