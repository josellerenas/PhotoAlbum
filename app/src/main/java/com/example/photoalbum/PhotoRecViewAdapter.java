package com.example.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoRecViewAdapter extends RecyclerView.Adapter<PhotoRecViewAdapter.ViewHolder> {

    // Declaring variables
    // NOTE: Here, I changed albumName from being an ArrayList to List
    private List<String> listNames, listImageUrl;
    private List<City> city;
    private Context mContext;
    private String userEmail;

//    // Constructor Singleton Pattern
//    private static PhotoRecViewAdapter recViewAdapter;
//
//    // I did this singleton because I wanted userEmail to stay the same and not being
//    // overwritten, but instead a problem emerged: the dataset doesn't update when we use
//    // the recview for 2nd time. Fix this, quitting the singleton and passing userEmail each
//    // time that is necessary. OR, create a updateDataset() method, keeping the singleton
//    public static PhotoRecViewAdapter getInstance(Context mContext, List<String> data,
//                                                  String userEmail) {
//        if (recViewAdapter == null) {
//            recViewAdapter = new PhotoRecViewAdapter(mContext, data, userEmail);
//        }
//        return recViewAdapter;
//    }

    // Normal Constructor
    PhotoRecViewAdapter(Context mContext, List<String> data, List<String> imageData,
                        String userEmail) {
        this.mContext = mContext;
        this.listNames = data;
        this.listImageUrl = imageData;
        this.userEmail = userEmail;
    }

    // OnCreateViewHolder defines the 'card' or the 'layout' we are gonna use to multiply in the recView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_album, viewGroup, false);
        return new ViewHolder(view);
    }

    // onBindViewHolder makes a 'foreach' loop in the data/albumName List.
    // Note: This event uses a 'ViewHolder' object. We should declare the class. It's at the end of
    // this document.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtAlbumName.setText(listNames.get(position));
        Picasso.get()
                .load(listImageUrl.get(position))
                .into(holder.imgAlbum);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext.toString().contains("LoginActivity")) {
                    Intent intent = new Intent(mContext, StampActivity.class);
                    intent.putExtra("city_name", listNames.get(position));
                    intent.putExtra("user_email", userEmail);
                    mContext.startActivity(intent);
                } else if (mContext.toString().contains("StampActivity")) {
                    Intent intent = new Intent(mContext, IndividualStampActivity.class);
                    intent.putExtra("stamp_name", listNames.get(position));
                    intent.putExtra("user_email", userEmail);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }

    public void setCity(List<City> city) {
        this.city = city;
        notifyDataSetChanged();
    }

    // Declaring ViewHolder class, which will be used in the onBindViewHolder event
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

