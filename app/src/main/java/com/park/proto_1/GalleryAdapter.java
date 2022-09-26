package com.park.proto_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.nio.file.attribute.AclEntryType;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    public ArrayList<String> localDataSet;
    private Activity activity;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
       public CardView cardView;

        public GalleryViewHolder(CardView view) {
            super(view);
            // Define click listener for the ViewHolder's View
            cardView = view;
        }
    }

    public GalleryAdapter(Activity activity, ArrayList<String> dataSet) {
        localDataSet = dataSet;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);

        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("profilePath",localDataSet.get(galleryViewHolder.getAdapterPosition()));
                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });

        return new GalleryViewHolder(cardView);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.photoView);
        Glide.with(activity).load(localDataSet.get(position)).centerCrop().override(500).into(imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
