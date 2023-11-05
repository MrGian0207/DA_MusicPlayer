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
import com.example.da_musicplayer.Data.AlbumsFavorite_Data;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.example.da_musicplayer.MainActivity;
import com.example.da_musicplayer.Manager.FavoriteManager;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchAlbumsAdapter extends RecyclerView.Adapter<SearchAlbumsAdapter.MyViewHolder> {

    private ArrayList<Albums> albums_list;
    private ArrayList<Albums> albums_list_temp;

    private Context mContext;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    boolean isFavorite;
    private FavoriteManager favoriteManager;

    public SearchAlbumsAdapter(Context context, ArrayList<Albums> albums_list, FavoriteManager favoriteManager) {
        this.mContext = context;
        this.albums_list = albums_list;
        this.favoriteManager = favoriteManager;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public SearchAlbumsAdapter(ArrayList<Albums> albums_list) {
        this.albums_list = albums_list;
    }

    public void setFilteredList(ArrayList<Albums> filteredList){
        this.albums_list = filteredList;
        notifyDataSetChanged();
    }
    ///////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public SearchAlbumsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_albums_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAlbumsAdapter.MyViewHolder holder, int position) {
        Albums album = new Albums(albums_list.get(position).getId(),
                albums_list.get(position).getSource_photo(),
                albums_list.get(position).getTitle_photo(),
                albums_list.get(position).getKey(),
                albums_list.get(position).getName(),
                albums_list.get(position).getSongs());

        Picasso.get().load(albums_list.get(position).getSource_photo()).into(holder.image_searchAlbums);
        holder.title_searchAlbums.setText(albums_list.get(position).getTitle_photo());
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        holder.image_searchAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Album.class);
                intent.putExtra("album",(Serializable) album);
                intent.putExtra("album_searchFragmentClicked",true);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return albums_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_searchAlbums;
        TextView title_searchAlbums;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_searchAlbums = itemView.findViewById(R.id.image_searchAlbums);
            title_searchAlbums = itemView.findViewById(R.id.title_searchAlbums);
        }
    }

}