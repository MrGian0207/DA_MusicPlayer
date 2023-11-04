package com.example.da_musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.da_musicplayer.Adapter.NavigationAdapter;
import com.example.da_musicplayer.Define.Artists;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Define.User;
import com.example.da_musicplayer.Fragment.HomeFragment;
import com.example.da_musicplayer.Fragment.SearchFragment;
import com.example.da_musicplayer.Fragment.YourLibraryFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    private String email;
    private String uid;
    private static User newUser;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        NavigationAdapter navigationAdapter = new NavigationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        navigationAdapter.addFragment(new HomeFragment(),"Home");
        navigationAdapter.addFragment(new SearchFragment(),"Search");
        navigationAdapter.addFragment(new YourLibraryFragment(),"YourLibrary");
        viewPager.setAdapter(navigationAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.baseline_home_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.baseline_search_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.baseline_view_headline_24);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("435223218602-a3138190hhhhvsbfutusosi3lmnkmv8a.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);
        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        newUser = new User(email,uid);
        databaseReference.child("Users").child(user.getUid()).setValue(newUser);
    }

    public static String uid_User(){
        return newUser.getUid();
    }
}