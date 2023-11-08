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

import com.example.da_musicplayer.ArtistsActivity;
import com.example.da_musicplayer.Define.Artist;
import com.example.da_musicplayer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {

    private ArrayList<Artist> artist_list;
    private Context mContext;

    public ArtistAdapter(Context context, ArrayList<Artist> artist_list) {
        this.mContext = context;
        this.artist_list = artist_list;
    }

    @NonNull
    @Override
    public ArtistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_artists_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.MyViewHolder holder, int position) {
        Artist artist = new Artist( artist_list.get(position).getId(),
                                    artist_list.get(position).getSource_photo(),
                                    artist_list.get(position).getTitle_photo(),
                                    artist_list.get(position).getKey());
        Picasso.get().load(artist_list.get(position).getSource_photo()).into(holder.image_topArtists);
        holder.title_topArtists.setText(artist_list.get(position).getTitle_photo());
        holder.image_topArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArtistsActivity.class);
                intent.putExtra("artist",(Serializable)artist);
                intent.putExtra("top_artistClicked",true);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artist_list.size();
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
