package com.park.proto_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class Api_main extends AppCompatActivity{

    // 어답터
    private ApiRecyclerAdapter adapter;
    // 리사이클러뷰
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);
        recyclerView = findViewById(R.id.Apirecyclerview);
        adapter = new ApiRecyclerAdapter();
        AssetManager assetManager = getAssets();

        try {
            InputStream is = assetManager.open("HasungData.json");
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


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
                    Log.i("dslkfjhskdfjhdl", row.getSigunNm());
                    row.setSpeciesNm(RowObj.getString("SPECIES_NM"));
                    row.setColorNm(RowObj.getString("COLOR_NM"));
                    row.setShterNm(RowObj.getString("SHTER_NM"));
                    row.setThumbImageCours(RowObj.getString("THUMB_IMAGE_COURS"));

                    // BookRecyclerAdapter에 Book
                    adapter.addItem(row);
                }
                adapter.notifyDataSetChanged();
            }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

}
