package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.MainActivity;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongOfAlbumAdapter extends RecyclerView.Adapter<SongOfAlbumAdapter.MyViewHolder>{

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Songs_Item> songs_of_album_list;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    boolean isFavorite;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public SongOfAlbumAdapter(ArrayList<Songs_Item> songs_of_album_list) {
        this.songs_of_album_list = songs_of_album_list;
    }

    public void setFilteredList(ArrayList<Songs_Item> filteredList){
        this.songs_of_album_list = filteredList;
        notifyDataSetChanged();
    }
    /////////////////////////////////////////////////////////////////////////////////////
    public SongOfAlbumAdapter(Context mContext, ArrayList<Songs_Item> songs_of_album_list) {
        this.songs_of_album_list = songs_of_album_list;
        this.mContext = mContext;
        this.sharedPreferences = mContext.getSharedPreferences("FavoriteSongs"+MainActivity.uid_User(), Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public SongOfAlbumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_of_album_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongOfAlbumAdapter.MyViewHolder holder, int position) {
        Songs_Item song_favourite = songs_of_album_list.get(position);
        // Trạng thái ban đầu
        isFavorite = sharedPreferences.getBoolean(song_favourite.getTitle_song(), false);

        if (isFavorite) {
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            databaseReference.child("Users")
                    .child(MainActivity.uid_User())
                    .child("listSongFavourite")
                    .child(song_favourite.getTitle_song())
                    .setValue(song_favourite);
            // Đánh dấu là yêu thích và đặt nền
            Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
            holder.favorite_song_btn.setBackground(favoriteDrawable);
        } else {
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            databaseReference.child("Users")
                    .child(MainActivity.uid_User())
                    .child("listSongFavourite")
                    .child(song_favourite.getTitle_song())
                    .removeValue();
            // Bỏ đánh dấu và đặt nền
            Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_border_24);
            holder.favorite_song_btn.setBackground(favoriteDrawable);
        }

        Picasso.get().load(songs_of_album_list.get(position).getSource_photo()).into(holder.image_songofalbum);
        holder.singer_songofalbum.setText(song_favourite.getSinger_song());
        holder.title_songofalbum.setText(song_favourite.getTitle_song());

        holder.favorite_song_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái của isFavorite tại đây
                isFavorite = !isFavorite;

                if (isFavorite) {
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listSongFavourite")
                            .child(song_favourite.getTitle_song())
                            .setValue(song_favourite);

                    // Bạn đã đánh dấu là yêu thích
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(song_favourite.getTitle_song(), true);
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
                    holder.favorite_song_btn.setBackground(favoriteDrawable);
                    editor.apply();

                    // Thực hiện các xử lý khi đánh dấu là yêu thích
                } else {
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listSongFavourite")
                            .child(song_favourite.getTitle_song())
                            .removeValue();
                    // Bỏ đánh dấu là yêu thích
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(song_favourite.getTitle_song(), false);
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_border_24);
                    holder.favorite_song_btn.setBackground(favoriteDrawable);
                    editor.apply();

                    // Thực hiện các xử lý khi bỏ đánh dấu là yêu thích
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs_of_album_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_songofalbum;
        TextView title_songofalbum;
        TextView singer_songofalbum;
        ImageView favorite_song_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_songofalbum = itemView.findViewById(R.id.image_songofalbum);
            title_songofalbum = itemView.findViewById(R.id.title_songofalbum);
            singer_songofalbum = itemView.findViewById(R.id.singer_songofalbum);
            favorite_song_btn = itemView.findViewById(R.id.favorite_song_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
