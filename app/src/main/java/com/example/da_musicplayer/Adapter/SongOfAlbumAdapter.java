package com.example.da_musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Fragment.YourLibraryFragment;
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SongOfAlbumAdapter(Context mContext, ArrayList<Songs_Item> songs_of_album_list) {
        this.songs_of_album_list = songs_of_album_list;
        this.mContext = mContext;
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

    @NonNull
    @Override
    public SongOfAlbumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_of_album_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongOfAlbumAdapter.MyViewHolder holder, int position) {
        Songs_Item song_favourite = new Songs_Item(songs_of_album_list.get(position).getId(),
                songs_of_album_list.get(position).getSource_photo(),
                songs_of_album_list.get(position).getTitle_song(),
                songs_of_album_list.get(position).getSinger_song(),
                songs_of_album_list.get(position).getLink_song());

        Picasso.get().load(songs_of_album_list.get(position).getSource_photo()).into(holder.image_songofalbum);
        holder.singer_songofalbum.setText(songs_of_album_list.get(position).getSinger_song());
        holder.title_songofalbum.setText(songs_of_album_list.get(position).getTitle_song());
        holder.favorite_song_btn.setOnClickListener(new View.OnClickListener() {
            Drawable buttonDrawable;
            Drawable.ConstantState buttonDrawableState;
            Drawable drawableToCompare = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
            boolean isFavorite = false;
            @Override
            public void onClick(View v) {
                buttonDrawable = holder.favorite_song_btn.getBackground();
                buttonDrawableState = buttonDrawable.getConstantState();
                if(!isFavorite){
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                                     .child(MainActivity.uid_User())
                                     .child("listSongFavourite")
                                     .child(song_favourite.getTitle_song())
                                     .setValue(song_favourite);
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_24);
                    holder.favorite_song_btn.setBackground(favoriteDrawable);
                    isFavorite = true;
                } else {
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listSongFavourite")
                            .child(song_favourite.getTitle_song())
                            .removeValue();
                    Drawable favoriteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_favorite_border_24);
                    holder.favorite_song_btn.setBackground(favoriteDrawable);
                    isFavorite = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs_of_album_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
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
