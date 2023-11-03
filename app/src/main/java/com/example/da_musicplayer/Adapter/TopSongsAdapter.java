package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class TopSongsAdapter extends RecyclerView.Adapter<TopSongsAdapter.MyViewHolder>{

    private ArrayList<Songs> songs_list;
    private Context mContext;

    public TopSongsAdapter(Context context, ArrayList<Songs> songs_list) {
        this.mContext = context;
        this.songs_list = songs_list;
    }

    @NonNull
    @Override
    public TopSongsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_songs_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSongsAdapter.MyViewHolder holder, int position) {

        Songs song = new Songs(songs_list.get(position).getId(),
                               songs_list.get(position).getSource_photo(),
                               songs_list.get(position).getTitle_photo(),
                               songs_list.get(position).getLink_song());

        Picasso.get().load(songs_list.get(position).getSource_photo()).into(holder.image_topSongs);
        holder.title_topSongs.setText(songs_list.get(position).getTitle_photo());

        holder.image_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Player.class);
                intent.putExtra("song",(Serializable) song);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs_list.size();
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
