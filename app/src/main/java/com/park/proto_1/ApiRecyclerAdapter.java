package com.park.proto_1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApiRecyclerAdapter extends RecyclerView.Adapter<ApiRecyclerAdapter.ViewHolder>{
    private ArrayList<ApiData> mApiData = new ArrayList<>();


    // 아이템 뷰를 저장하는 viewholder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ApiTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ApiTextView = itemView.findViewById(R.id.ApiTextView);


        }

        void onBind(ApiData data1) {
            ApiTextView.setText(data1.getApiDogInfo());

        }
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 return
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_open_protect_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mApiData.get(position));
    }




    // 사이즈
    @Override
    public int getItemCount() {
        return mApiData.size();
    }

    // data 모델의 객체들을 list에 저장
    public void setmovieList(ArrayList<ApiData> list) {
        this.mApiData = list;
        notifyDataSetChanged();
    }
}
