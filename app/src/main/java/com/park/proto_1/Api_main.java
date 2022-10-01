package com.park.proto_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Api_main extends AppCompatActivity{

    // 어답터
    private ApiRecyclerAdapter adapter;
    // 리사이클러뷰
    private RecyclerView recyclerView;
    EditText editText;
    public Spinner spinner;
    private Context mContext = Api_main.this;
    ArrayList<Row> search_list = new ArrayList<>();
    ArrayList<Row> original_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);
        recyclerView = findViewById(R.id.Apirecyclerview);
        spinner = findViewById(R.id.search_spinner);
        adapter = new ApiRecyclerAdapter();
        editText = findViewById(R.id.edit_search);
        AllReStart();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.api);

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


    }

    public void EditText(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if(searchText.equals("")){
                    adapter.setItems(original_list);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < original_list.size(); a++) {
                        if (original_list.get(a).getSpeciesNm().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(original_list.get(a));
                        }
                        adapter.setItems(search_list);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        AllReStart();
        EditText();
    }

    public void AllReStart(){
        ArrayList<String> Spinnerlist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Spinnerlist.add("모든지역");
        Spinnerlist.add("화성시");
        Spinnerlist.add("수원시");
        Spinnerlist.add("성남시");
        Spinnerlist.add("부천시");

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

//        EditText();
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
                original_list.add(row);
                adapter.addItem(row);

//                adapter.notifyDataSetChanged();

            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


}
