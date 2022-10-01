package com.park.proto_1;

import static com.park.proto_1.Util.isArchiveStorageUrl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>{
    private ArrayList<PostInfo> mDataSet;
    private Activity activity;
    private FirebaseFirestore firebaseFirestore;
    private OnPostListener onPostListener;

    static class ArchiveViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ArchiveViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    public ArchiveAdapter(Activity activity, ArrayList<PostInfo> dataSet) {
        this.mDataSet = dataSet;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public void setArchiveAdapter(Activity activity, ArrayList<PostInfo> dataSet){
        this.mDataSet = dataSet;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
        notifyDataSetChanged();
    }
    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }


    @NonNull
    @Override
    public ArchiveAdapter.ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final ArchiveViewHolder archiveViewHolder = new ArchiveViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("게시글 열람 알림"); //제목
                dlg.setMessage("게시글 상세보기시 100point 차감됩니다."); // 메시지
//                dlg.setIcon(R.drawable.deum); // 아이콘 설정
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        MinusPoint();
                        Intent intent = new Intent(activity, ArchivePostActivity.class);
                        intent.putExtra("postInfo", mDataSet.get(archiveViewHolder.getAdapterPosition()));
                        activity.startActivity(intent);
                    }
                });
                dlg.show();

            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, archiveViewHolder.getAdapterPosition());
            }
        });
        return archiveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ArchiveViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataSet.get(position).getTitle());
        TextView phonT = cardView.findViewById(R.id.phoneNumber);
        phonT.setText("전화번호: " + mDataSet.get(position).getPhoneNumber());

        TextView createdAtTextView = cardView.findViewById(R.id.createAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(mDataSet.get(position).getCreatedAt()));

        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList = mDataSet.get(position).getContents();



        if(contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)) {
            contentsLayout.setTag(contentsList);
            contentsLayout.removeAllViews();
            final int MORE_INDEX = 2;
            for (int i = 0; i < contentsList.size(); i++) {
                if (i == MORE_INDEX) {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText("더보기...");
                    contentsLayout.addView(textView);
                    break;
                }
                String contents = contentsList.get(i);
                if (isArchiveStorageUrl(contents)) {
                    ImageView imageView = new ImageView(activity);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    contentsLayout.addView(imageView);
                    Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                } else {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(contents);
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    contentsLayout.addView(textView);
                }
            }
        }
    }
    private void MinusPoint(){
        FirebaseFirestore pointDb = FirebaseFirestore.getInstance();
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference DR = pointDb.collection("users").document(user.getUid());
        DR.update("point", FieldValue.increment(-100));
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:
                        onPostListener.onModify(position);
                        return true;
                    case R.id.delete:
                        onPostListener.onDelete(position);
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }
}
