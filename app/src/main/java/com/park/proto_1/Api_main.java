package com.park.proto_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AllPermission;
import java.util.ArrayList;


public class Api_main extends AppCompatActivity{

    // 어답터
    private ApiRecyclerAdapter adapter;
    // 리사이클러뷰
    private RecyclerView recyclerView;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);
        recyclerView = findViewById(R.id.Apirecyclerview);
        spinner = findViewById(R.id.search_spinner);

        adapter = new ApiRecyclerAdapter();
        ArrayList<String> Spinnerlist = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        //2. 데이터 담기
        Spinnerlist.add("모든지역");
        Spinnerlist.add("화성시");
        Spinnerlist.add("수원시");
        Spinnerlist.add("성남시");
        Spinnerlist.add("부천시");

        //3. 스피너에 리스트 적용
        spinner.setAdapter(new ArrayAdapter<>(Api_main.this
                , android.R.layout.simple_spinner_dropdown_item, Spinnerlist));
        SpinnerUsing(spinner);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }
    public void SpinnerUsing(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                adapter.clear();

                //선택한 데이터를 변수에 넣다.
                String number = adapterView.getItemAtPosition(position).toString();
                adapter.notifyDataSetChanged();
                if(number == "모든지역") {
                    String AllData = "AllData.json";
                    ReadJson(AllData);
                }else if(number == "화성시"){
                    String AllData = "HasungData.json";
                    ReadJson(AllData);
                }else if(number == "수원시"){
                    String AllData = "SuwanData.json";
                    ReadJson(AllData);
                }
                else if(number == "성남시"){
                    String AllData = "SungnamData.json";
                    ReadJson(AllData);
                }else if(number == "부천시"){
                    String AllData = "BucheonData.json";
                    ReadJson(AllData);
                }

                Log.i("eventttt", number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String AllData = "AllData.json";
                ReadJson(AllData);
            }

        });
    }
    public void ReadJson(String file){
        AssetManager assetManager = getAssets();

        try {
            InputStream is = assetManager.open(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            String json = buffer.toString();

            jsonP(json);


        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }
    public void jsonP(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray rowArr = jsonObject.getJSONArray("row");

            for (int i = 0; i < rowArr.length(); i++) {
                JSONObject RowObj = null;
                try {
                    RowObj = rowArr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Row row = new Row();
                row.setSigunNm(RowObj.getString("SIGUN_NM"));
                row.setSpeciesNm(RowObj.getString("SPECIES_NM"));
                row.setColorNm(RowObj.getString("COLOR_NM"));
                row.setShterNm(RowObj.getString("SHTER_NM"));
                row.setThumbImageCours(RowObj.getString("THUMB_IMAGE_COURS"));

                adapter.addItem(row);
                adapter.notifyDataSetChanged();

            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
