package com.example.da_musicplayer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.MainActivity;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongsOfYourLibraryAdapter extends RecyclerView.Adapter<SongsOfYourLibraryAdapter.MyViewHolder> {

    private ArrayList<Songs_Item> songs_of_favoriteList;
    private Context mContext;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private Boolean isRemove;
    int removePosition;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private SongOfAlbumAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SongOfAlbumAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SongsOfYourLibraryAdapter(Context mContext, ArrayList<Songs_Item> songs_of_favoriteList) {
        this.songs_of_favoriteList = songs_of_favoriteList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SongsOfYourLibraryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_yourlibrary_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsOfYourLibraryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Songs_Item song = songs_of_favoriteList.get(position);
        removePosition = position;
        Picasso.get().load(songs_of_favoriteList.get(position).getSource_photo()).into(holder.image_itemOfYourLibrary);
        holder.title_itemOfYourLibrary.setText(songs_of_favoriteList.get(position).getTitle_song());
        holder.clear_itemOfYourLibrary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);

                sharedPreferences = mContext.getSharedPreferences("FavoriteSongs"+MainActivity.uid_User(),Context.MODE_PRIVATE);
                isRemove = sharedPreferences.getBoolean(song.getTitle_song(), false);
                if(isRemove) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(song.getTitle_song(), false);
                    editor.apply();
                   if(removePosition >= 0){
                       removeSong(song);
                       notifyItemRemoved(removePosition);
                   }
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference();
                    databaseReference.child("Users")
                            .child(MainActivity.uid_User())
                            .child("listSongFavourite")
                            .child(song.getTitle_song())
                            .removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs_of_favoriteList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_itemOfYourLibrary;
        TextView title_itemOfYourLibrary;
        TextView about_itemOfYourLibrary;
        ImageView clear_itemOfYourLibrary_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_itemOfYourLibrary = itemView.findViewById(R.id.image_itemOfYourLibrary);
            title_itemOfYourLibrary = itemView.findViewById(R.id.title_itemOfYourLibrary);
            about_itemOfYourLibrary = itemView.findViewById(R.id.about_itemOfYourLibrary);
            clear_itemOfYourLibrary_btn = itemView.findViewById(R.id.clear_itemOfYourLibrary_btn);

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
/////////////////////////////////////////////////////////////////////////////////////////////
    public void addSong(Songs_Item song){
        songs_of_favoriteList.add(song);
    }

    public void updateSong(int position, Songs_Item song){
        songs_of_favoriteList.set(position, song);
    }

    public void removeSong(Songs_Item song) {
        songs_of_favoriteList.remove(song);
    }

    public int findAlbumPosition(Songs_Item song) {
        return songs_of_favoriteList.indexOf(song);
    }
////////////////////////////////////////////////////////////////////////////////////////////
}
