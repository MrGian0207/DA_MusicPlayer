package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Album;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.MyViewHolder> {

    private ArrayList<Albums> albums_list;
    private Context mContext;

    public TopAlbumsAdapter(Context context, ArrayList<Albums> albums_list) {
        this.mContext = context;
        this.albums_list = albums_list;
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
                albums_list.get(position).getName());

        Picasso.get().load(albums_list.get(position).getSource_photo()).into(holder.image_topAlbums);
        holder.title_topAlbums.setText(albums_list.get(position).getTitle_photo());

        holder.image_topAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Album.class);
                intent.putExtra("album",(Serializable) album);
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_topAlbums = itemView.findViewById(R.id.image_topAlbums);
            title_topAlbums = itemView.findViewById(R.id.title_topAlbums);
        }
    }
}
