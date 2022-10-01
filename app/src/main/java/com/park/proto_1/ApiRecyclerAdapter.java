package com.park.proto_1;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ApiRecyclerAdapter extends RecyclerView.Adapter<ApiRecyclerAdapter.ItemViewHolder>{

//    private List<AbdmAnimalProtect> dataList;
//    private Context context;

    ArrayList<Row> rowList = new  ArrayList<Row>();





    @Override
    public ApiRecyclerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_open_protect_info, parent, false);
        return new ItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(rowList.get(position));
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    public void addItem(Row row) {
       rowList.add(row);
    }

    public void clear(){
        rowList.clear();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private ImageView imageView1;

        private String imgURL;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.SIGUN_NM);
            textView2 = itemView.findViewById(R.id.SPECIES_NM);
            textView3 = itemView.findViewById(R.id.COLOR_NM);
            textView4 = itemView.findViewById(R.id.SHTER_NM);
            imageView1 = itemView.findViewById(R.id.ApiImageView);
        }

        // 실제 데이터를 1개의 객체마다 1:1 대응하여 바인딩시킨다.
        void onBind(Row row) {
            textView1.setText(row.getSigunNm());
            textView2.setText(row.getSpeciesNm());
            textView3.setText(row.getColorNm());
            textView4.setText(row.getShterNm());
            String imgURL = row.getThumbImageCours();

            // Glide URL로 이미지 불러오기 오픈소스
            Glide.with(itemView).load(imgURL).into(imageView1);
        }
    }
    public void setItems(ArrayList<Row> SearchItem){
        rowList = SearchItem;
        notifyDataSetChanged();
    }

}
