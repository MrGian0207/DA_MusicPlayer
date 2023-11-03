package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Artists;
import com.example.da_musicplayer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopArtistsAdapter extends RecyclerView.Adapter<TopArtistsAdapter.MyViewHolder> {

    private ArrayList<Artists> artists_list;
    private Context mContext;

    public TopArtistsAdapter(Context context, ArrayList<Artists> albums_list) {
        this.mContext = context;
        this.artists_list = albums_list;
    }

    @NonNull
    @Override
    public TopArtistsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_artists_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopArtistsAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(artists_list.get(position).getSource_photo()).into(holder.image_topArtists);
        holder.title_topArtists.setText(artists_list.get(position).getTitle_photo());
    }

    @Override
    public int getItemCount() {
        return artists_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_topArtists;
        TextView title_topArtists;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_topArtists = itemView.findViewById(R.id.image_topArtists);
            title_topArtists = itemView.findViewById(R.id.title_topArtists);
        }
    }
}
