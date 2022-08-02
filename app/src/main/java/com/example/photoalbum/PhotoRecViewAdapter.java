package com.example.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotoRecViewAdapter extends RecyclerView.Adapter<PhotoRecViewAdapter.ViewHolder> {

    // Declaring variables
    // NOTE: Here, I changed albumName from being an ArrayList to List
    private List<String> albumName;
    private List<City> city;
    private Context mContext;
    private String userEmail;

    // Constructor Singleton Pattern
    private static PhotoRecViewAdapter recViewAdapter;

    // TODO I did this singleton because I wanted userEmail to stay the same and not being
    // overwrited, but instead a problem emerged: the dataset doesn't update when we use
    // the recview for 2nd time. Fix this, quitting the singleton and passing userEmail each
    // time that is neccessary. OR, create a updateDataset() method, keeping the singleton
    public static PhotoRecViewAdapter getInstance(Context mContext, List<String> data,
                                                  String userEmail) {
        if (recViewAdapter == null) {
            recViewAdapter = new PhotoRecViewAdapter(mContext, data, userEmail);
        }
        return recViewAdapter;
    }

    private PhotoRecViewAdapter(Context mContext, List<String> data,
                                String userEmail) {
        this.mContext = mContext;
        this.albumName = data;
        this.userEmail = userEmail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_album, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtAlbumName.setText(albumName.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext.toString().contains("LoginActivity")) {
                    Intent intent = new Intent(mContext, StampActivity.class);
                    intent.putExtra("city_name", albumName.get(position));
                    mContext.startActivity(intent);
                } else if (mContext.toString().contains("StampActivity")) {
                    Intent intent = new Intent(mContext, IndividualStampActivity.class);
                    intent.putExtra("stamp_name", albumName.get(position));
                    intent.putExtra("user_email", userEmail);
                    mContext.startActivity(intent);
                }
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumName.size();
    }

    public void setCity(List<City> city) {
        this.city = city;
        notifyDataSetChanged();
    }

    public void updateDataset(List<String> albumName) {
        this.albumName = albumName;
    }

    public void updateContext(Context mContext) {
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imgAlbum;
        private TextView txtAlbumName, txtStampsDone, txtStampsGoal, txtPercent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.parentCardView);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
            txtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            txtStampsDone = itemView.findViewById(R.id.txtStampsDone);
            txtStampsGoal = itemView.findViewById(R.id.txtStampsGoal);
            txtPercent = itemView.findViewById(R.id.txtPercent);
        }
    }
}

