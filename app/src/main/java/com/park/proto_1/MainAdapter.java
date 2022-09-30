package com.park.proto_1;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GalleryViewHolder>{
    public ArrayList<PostInfo> mDataSet;
    private final Activity activity;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
       public CardView cardView;

        public GalleryViewHolder(Activity activity, CardView view, PostInfo postInfo) {
            super(view);
            cardView = view;

            LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = postInfo.getContents();


            if(contentsLayout.getChildCount() == 0){
                for(int i=0; i<contentsList.size(); i++){
                    String contents = contentsList.get(i);
                    if(Patterns.WEB_URL.matcher(contents).matches()){
                        ImageView imageView = new ImageView(activity);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        contentsLayout.addView(imageView);
                        Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                    }else{
                        TextView textView = new TextView(activity);
                        textView.setLayoutParams(layoutParams);
                        //textView.setText(contents);
                        contentsLayout.addView(textView);
                    }
                }
            }
        }
    }

    public MainAdapter(Activity activity, ArrayList<PostInfo> dataSet) {
        mDataSet = dataSet;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public MainAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(activity, cardView, mDataSet.get(viewType));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataSet.get(position).getTitle());

        TextView createdAtTextView = cardView.findViewById(R.id.creatAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(mDataSet.get(position).getCreatedAt()));

        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ArrayList<String> contentsList = mDataSet.get(position).getContents();


        for(int i=0; i<contentsList.size(); i++){
            String contents = contentsList.get(i);
            if(Patterns.WEB_URL.matcher(contents).matches()){
                Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into((ImageView) contentsLayout.getChildAt(i));
            }else{
                ((TextView)contentsLayout.getChildAt(i)).setText(contents);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
