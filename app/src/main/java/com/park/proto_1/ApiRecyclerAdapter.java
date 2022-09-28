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

public class ApiRecyclerAdapter extends RecyclerView.Adapter<ApiRecyclerAdapter.CustomViewHolder>{

    private List<AbdmAnimalProtect> dataList;
    private Context context;
    private List<Row> rowlist;
    public ApiRecyclerAdapter(Context context, List<AbdmAnimalProtect> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.DogInfo);
            coverImage = mView.findViewById(R.id.ApiImageView);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_open_protect_info, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        // 타이틀
        holder.txtTitle.setText(dataList.get(position).getRow().toString());
        Log.i("타이트트ㅡ으ㅡ트", dataList.toString());
//        // 이미지
        Glide.with(context)
                .load(rowlist.get(position).getThumbImageCours())
                .skipMemoryCache(true)
                .circleCrop()
                .skipMemoryCache(true)
                .error(rowlist.get(position).getThumbImageCours())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
