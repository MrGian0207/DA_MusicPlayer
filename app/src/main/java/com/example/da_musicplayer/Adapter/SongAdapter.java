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

import com.example.da_musicplayer.Define.Song;
import com.example.da_musicplayer.PlayerActivity;
import com.example.da_musicplayer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder>{

    private ArrayList<Song> song_list;
    private Context mContext;

    public SongAdapter(Context context, ArrayList<Song> song_list) {
        this.mContext = context;
        this.song_list = song_list;
    }

    @NonNull
    @Override
    public SongAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_songs_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.MyViewHolder holder, int position) {

        Song song = new Song(song_list.get(position).getId(),
                               song_list.get(position).getSource_photo(),
                               song_list.get(position).getTitle_photo(),
                               song_list.get(position).getLink_song());

        Picasso.get().load(song_list.get(position).getSource_photo()).into(holder.image_topSongs);
        holder.title_topSongs.setText(song_list.get(position).getTitle_photo());

        holder.image_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);
                intent.putExtra("song",(Serializable) song);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return song_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_topSongs;
        TextView title_topSongs;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_topSongs = itemView.findViewById(R.id.image_topSongs);
            title_topSongs = itemView.findViewById(R.id.title_topSongs);
        }
    }
}
