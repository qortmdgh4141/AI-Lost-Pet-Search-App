package com.park.proto_1;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    public ArrayList<String> mDataSet;
    private Activity activity;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
       public CardView cardView;

        public GalleryViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    public GalleryAdapter(Activity activity, ArrayList<String> dataSet) {
        mDataSet = dataSet;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);
        cardView.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("profilePath",mDataSet.get(galleryViewHolder.getAdapterPosition()));
            activity.setResult(Activity.RESULT_OK, resultIntent);
            activity.finish();
        });

        return galleryViewHolder;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.photoView);
        Glide.with(activity).load(mDataSet.get(position)).centerCrop().override(500).into(imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
