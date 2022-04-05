package com.example.photoalbum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhotoRecViewAdapter extends RecyclerView.Adapter<PhotoRecViewAdapter.ViewHolder> {

    // Declaring variables
    private ArrayList<String> albumName;

    // Constructor
    public PhotoRecViewAdapter(ArrayList<String> data) {
        this.albumName = data;
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
    }

    @Override
    public int getItemCount() {
        return albumName.size();
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
