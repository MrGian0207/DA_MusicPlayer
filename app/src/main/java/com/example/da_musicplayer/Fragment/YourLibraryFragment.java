package com.example.da_musicplayer.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da_musicplayer.Login.LoginActivity;
import com.example.da_musicplayer.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

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

////////////////////////////////////////////////////////////////////// Xử lí trong Your Library //////


    }
}