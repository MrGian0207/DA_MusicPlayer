package com.example.da_musicplayer.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da_musicplayer.Adapter.ItemsOfYourLibraryAdapter;
import com.example.da_musicplayer.Adapter.SongOfAlbumAdapter;
import com.example.da_musicplayer.Adapter.TopAlbumsAdapter;
import com.example.da_musicplayer.Data.AlbumsFavorite_Data;
import com.example.da_musicplayer.Data.SongsFavorite_Data;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.example.da_musicplayer.Login.LoginActivity;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YourLibraryFragment extends Fragment {

    Button btnLogOut;
    TextView txtUser;
    ImageView imageProfile;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    RecyclerView playlist_favourite;
    RecyclerView albumlist_favourite;
    ItemsOfYourLibraryAdapter songOfYourLibraryAdapter;
    TopAlbumsAdapter albumOfYourLibraryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        txtUser = (TextView) view.findViewById(R.id.txtUser);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("435223218602-a3138190hhhhvsbfutusosi3lmnkmv8a.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        user = firebaseAuth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            txtUser.setText(user.getEmail());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                googleSignInClient.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

//////////////////////////////////////// Xử lí trong Your Library //////////////////////////////////////
        SongsFavorite_Data.generateSongsItem(new SongsItemCallback() {
            @Override
            public void onSongsItemLoaded(ArrayList<Songs_Item> SongsFavouriteList) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                playlist_favourite = view.findViewById(R.id.playlist_favourite);
                playlist_favourite.setLayoutManager(layoutManager);
                songOfYourLibraryAdapter = new ItemsOfYourLibraryAdapter(getContext(), SongsFavouriteList);
                playlist_favourite.setAdapter(songOfYourLibraryAdapter);
                songOfYourLibraryAdapter.setOnItemClickListener(new SongOfAlbumAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), Player.class);
                        intent.putExtra("favouriteSong_list", SongsFavouriteList);
                        intent.putExtra("position_song_favourite", position);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onSongsItemLoadError(String errorMessage) {
            }
        },user.getUid());
////////////////////////////////////////////////////////////////////////////////////////////////////////
        AlbumsFavorite_Data.generateAlbumFavouriteItem(new AlbumsCallback() {
            @Override
            public void onAlbumsLoaded(ArrayList<Albums> albums) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                albumlist_favourite = view.findViewById(R.id.albumlist_favourite);
                albumlist_favourite.setLayoutManager(gridLayoutManager);
                albumOfYourLibraryAdapter = new TopAlbumsAdapter(getContext(), albums);
                albumlist_favourite.setAdapter(albumOfYourLibraryAdapter);
            }
            @Override
            public void onAlbumsLoadError(String errorMessage) {
            }
        },user.getUid());
    }
}