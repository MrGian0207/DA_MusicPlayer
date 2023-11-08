package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.AlbumActivity;
import com.example.da_musicplayer.Define.Album;
import com.example.da_musicplayer.MainActivity;
import com.example.da_musicplayer.Manager.FavoriteManager;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private ArrayList<Album> album_list;
    private Context mContext;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    boolean isFavorite;
    private FavoriteManager favoriteManager;

    public AlbumAdapter(Context context, ArrayList<Album> album_list, FavoriteManager favoriteManager) {
        this.mContext = context;
        this.album_list = album_list;
//        this.sharedPreferences = mContext.getSharedPreferences("FavoriteAlbums"+MainActivity.uid_User(), Context.MODE_PRIVATE);
        this.favoriteManager = favoriteManager;
    }
////////////////////////////////////////////////////////////////////////////////////
    public AlbumAdapter(ArrayList<Album> album_list) {
        this.album_list = album_list;
    }

    public void setFilteredList(ArrayList<Album> filteredList){
        this.album_list = filteredList;
        notifyDataSetChanged();
    }
///////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_albums_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.MyViewHolder holder, int position) {
        Album album = new Album(album_list.get(position).getId(),
                album_list.get(position).getSource_photo(),
                album_list.get(position).getTitle_photo(),
                album_list.get(position).getKey(),
                album_list.get(position).getName(),
                album_list.get(position).getSongs());

        Picasso.get().load(album_list.get(position).getSource_photo()).into(holder.image_topAlbums);
        holder.title_topAlbums.setText(album_list.get(position).getTitle_photo());
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Trạng thái ban đầu
        isFavorite = favoriteManager.isFavorite(album.getKey());

        if (isFavorite) {
            Album album_favourite = new Album(album.getId(),
                    album.getSource_photo(),
                    album.getTitle_photo(),
                    album.getKey(),
                    album.getName(),
                    album.getSongs());

            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            databaseReference.child("Users")
                    .child(MainActivity.uid_User())
                    .child("listAlbumFavourite")
                    .child(album.getKey())
                    .setValue(album_favourite);
            // Đánh dấu là yêu thích và đặt nền
            Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
            holder.favorite_album_btn.setBackground(favoriteDrawable);
        } else {
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            databaseReference.child("Users")
                    .child(MainActivity.uid_User())
                    .child("listAlbumFavourite")
                    .child(album.getKey())
                    .removeValue();
            // Bỏ đánh dấu và đặt nền
            Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_border_24);
            holder.favorite_album_btn.setBackground(favoriteDrawable);
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        holder.favorite_album_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if (isFavorite) {
                    Album album_favourite = new Album(album.getId(),
                            album.getSource_photo(),
                            album.getTitle_photo(),
                            album.getKey(),
                            album.getName(),
                            album.getSongs());

                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listAlbumFavourite")
                            .child(album.getKey())
                            .setValue(album_favourite);

                    // Bạn đã đánh dấu là yêu thích
                    favoriteManager.setFavorite(album.getKey(), true);
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
                    holder.favorite_album_btn.setBackground(favoriteDrawable);
                    // Thực hiện các xử lý khi đánh dấu là yêu thích
                } else {
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listAlbumFavourite")
                            .child(album.getKey())
                            .removeValue();
                    // Bỏ đánh dấu là yêu thích
                    favoriteManager.setFavorite(album.getKey(), false);
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_border_24);
                    holder.favorite_album_btn.setBackground(favoriteDrawable);
                    // Thực hiện các xử lý khi bỏ đánh dấu là yêu thích
                }
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        holder.image_topAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AlbumActivity.class);
                intent.putExtra("album",(Serializable) album);
                intent.putExtra("top_albumClicked",true);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return album_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_topAlbums;
        TextView title_topAlbums;
        ImageView favorite_album_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_topAlbums = itemView.findViewById(R.id.image_topAlbums);
            title_topAlbums = itemView.findViewById(R.id.title_topAlbums);
            favorite_album_btn = itemView.findViewById(R.id.favorite_album_btn);
        }
    }
}
