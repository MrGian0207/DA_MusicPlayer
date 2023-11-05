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

import com.example.da_musicplayer.Album;
import com.example.da_musicplayer.Data.Songs_Item_Data;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.example.da_musicplayer.MainActivity;
import com.example.da_musicplayer.Manager.FavoriteManager;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.MyViewHolder> {

    private ArrayList<Albums> albums_list;
    private Context mContext;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    boolean isFavorite;
    private FavoriteManager favoriteManager;

    public TopAlbumsAdapter(Context context, ArrayList<Albums> albums_list, FavoriteManager favoriteManager) {
        this.mContext = context;
        this.albums_list = albums_list;
//        this.sharedPreferences = mContext.getSharedPreferences("FavoriteAlbums"+MainActivity.uid_User(), Context.MODE_PRIVATE);
        this.favoriteManager = favoriteManager;
    }
////////////////////////////////////////////////////////////////////////////////////
    public TopAlbumsAdapter(ArrayList<Albums> albums_list) {
        this.albums_list = albums_list;
    }

    public void setFilteredList(ArrayList<Albums> filteredList){
        this.albums_list = filteredList;
        notifyDataSetChanged();
    }
///////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public TopAlbumsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_albums_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopAlbumsAdapter.MyViewHolder holder, int position) {
        Albums album = new Albums(albums_list.get(position).getId(),
                albums_list.get(position).getSource_photo(),
                albums_list.get(position).getTitle_photo(),
                albums_list.get(position).getKey(),
                albums_list.get(position).getName(),
                albums_list.get(position).getSongs());

        Picasso.get().load(albums_list.get(position).getSource_photo()).into(holder.image_topAlbums);
        holder.title_topAlbums.setText(albums_list.get(position).getTitle_photo());
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Trạng thái ban đầu
        isFavorite = favoriteManager.isFavorite(album.getKey());

        if (isFavorite) {
            Albums album_favourite = new Albums(album.getId(),
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
                    Albums album_favourite = new Albums(album.getId(),
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
                Intent intent = new Intent(v.getContext(), Album.class);
                intent.putExtra("album",(Serializable) album);
                intent.putExtra("top_albumClicked",true);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums_list.size();
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
