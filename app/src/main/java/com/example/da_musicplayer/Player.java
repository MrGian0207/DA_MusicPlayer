package com.example.da_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Define.Songs_Item;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    ImageView image_player;
    ImageButton btn_PlayPause;
    ImageButton btn_prev;
    ImageButton btn_next;
    TextView textCurrentTime, textTotalDuration;
    SeekBar playerSeekbar;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    Intent intent_songofalbum;
    Intent intent_TopSongs;
    Intent intent_song_FromSearchInAlbum;
    Intent intent_songFromSearchFragment;
    ArrayList<Songs_Item> songsList;
    protected String linkSong;
    int position;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        image_player = findViewById(R.id.image_player);
        btn_PlayPause = findViewById(R.id.btn_play);
        btn_prev = findViewById(R.id.btn_precious);
        btn_next = findViewById(R.id.btn_next);
        textCurrentTime = findViewById(R.id.textCurrentTime);
        textTotalDuration = findViewById(R.id.textTotalDuration);
        playerSeekbar = findViewById(R.id.playerSeekbar);
        mediaPlayer = new MediaPlayer();
////////////////////// Get nhạc từ TopSongs//////////////////////////////////////////////////
        intent_TopSongs = getIntent();
        Songs top_song = (Songs) intent_TopSongs.getSerializableExtra("song");
        if(top_song != null) {
            Picasso.get()
                    .load(top_song.getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = top_song.getLink_song();
        }
///////////////////// Get nhạc từ trong searchFragment///////////////////////////////////////
        intent_song_FromSearchInAlbum = getIntent();
        Songs_Item song_from_searchFragment = (Songs_Item) intent_song_FromSearchInAlbum.getSerializableExtra("song_from_searchFragment");
        if(song_from_searchFragment != null) {
            Picasso.get()
                    .load(song_from_searchFragment.getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = song_from_searchFragment.getLink_song();
        }
///////////////////// Get nhạc từ trong từng album of TopAlbums
        intent_songofalbum = getIntent();
        ArrayList<Songs_Item> songs_of_album_list = (ArrayList<Songs_Item>) intent_songofalbum.getSerializableExtra("songs_album_list");
        position = (int) intent_songofalbum.getIntExtra("position_song",-1);
        if (songs_of_album_list != null && position >= 0){
            Picasso.get()
                    .load(songs_of_album_list.get(position).getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = songs_of_album_list.get(position).getLink_song();
        }
//////////////////// Get nhạc từ search trong từng album of Search_In_Album
        intent_song_FromSearchInAlbum = getIntent();
        ArrayList<Songs_Item> songs_fromSearchInAlbum = (ArrayList<Songs_Item>) intent_song_FromSearchInAlbum.getSerializableExtra("songs_album_list_fromSearchInAlbum");
        position = (int) intent_songofalbum.getIntExtra("position_song_fromSearchInAlbum",-1);
        if (songs_fromSearchInAlbum != null && position >= 0){
            Picasso.get()
                    .load(songs_fromSearchInAlbum.get(position).getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = songs_fromSearchInAlbum.get(position).getLink_song();
        }
///////////////////// Get list nhạc tồn tại//////////////////////////////////////////////////////////////
        if (songs_of_album_list != null){
            songsList = songs_of_album_list;
        } else if (songs_fromSearchInAlbum != null) {
            songsList = songs_fromSearchInAlbum;
        }
///////////////////////////////////////////////////////////////////////////
        playerSeekbar.setMax(100);
        btn_PlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btn_PlayPause.setBackgroundResource(R.drawable.baseline_play_circle_filled_24);
                }else{
                    prepareMediaPlayer();
                    mediaPlayer.start();
                    btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                    updateSeekBar();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(top_song==null && song_from_searchFragment == null ){
                position++;
                if(position > songsList.size()-1){
                    position = 0;
                }
                if (songsList != null && position >= 0){
                    Picasso.get()
                            .load(songsList.get(position).getSource_photo())
                            .resize(2000, 2000)
                            .centerCrop()
                            .into(image_player);}
                linkSong = songsList.get(position).getLink_song();
                if(mediaPlayer.isPlaying())
                    {   mediaPlayer.stop();
                        mediaPlayer.reset();
                        btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                        playerSeekbar.setProgress(0);
                        textCurrentTime.setText("00:00");
                        prepareMediaPlayer();
                        mediaPlayer.start();

                    }
                else{
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                    playerSeekbar.setProgress(0);
                    prepareMediaPlayer();
                    mediaPlayer.start();
                    }
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (top_song == null && song_from_searchFragment == null) {
                    position--;
                    if (position < 0) {
                        position = songsList.size() - 1;
                    }
                    if (songsList != null && position >= 0) {
                        Picasso.get()
                                .load(songsList.get(position).getSource_photo())
                                .resize(2000, 2000)
                                .centerCrop()
                                .into(image_player);
                    }
                    linkSong = songsList.get(position).getLink_song();
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                        playerSeekbar.setProgress(0);
                        textCurrentTime.setText("00:00");
                        prepareMediaPlayer();
                        mediaPlayer.start();

                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                        playerSeekbar.setProgress(0);
                        prepareMediaPlayer();
                        mediaPlayer.start();
                    }
                }
            }
        });


        playerSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekbar.setSecondaryProgress(percent);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (top_song == null && song_from_searchFragment == null) {
                    position++;
                    if (position > songsList.size() - 1) {
                        position = 0;
                    }
                    if (songsList != null && position >= 0) {
                        Picasso.get()
                                .load(songsList.get(position).getSource_photo())
                                .resize(2000, 2000)
                                .centerCrop()
                                .into(image_player);
                    }
                    linkSong = songsList.get(position).getLink_song();
                    mediaPlayer.reset();
                    textCurrentTime.setText("00:00");
                    playerSeekbar.setProgress(0);
                    prepareMediaPlayer();
                    mediaPlayer.start();
                    btn_PlayPause.setBackgroundResource(R.drawable.baseline_pause_circle_filled_24);
                }
                else {
                    mediaPlayer.reset();
                    textCurrentTime.setText("00:00");
                    playerSeekbar.setProgress(0);
                    btn_PlayPause.setBackgroundResource(R.drawable.baseline_play_circle_filled_24);
                }
            }
        });

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop(); // Dừng phát nhạc
//            }
//            mediaPlayer.release(); // Giải phóng tài nguyên của MediaPlayer
//        }
//    }

    protected void prepareMediaPlayer() {
        try{
            mediaPlayer.setDataSource(linkSong);
            mediaPlayer.prepare();
            textTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        } catch (Exception exception){
//            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()){
            playerSeekbar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())*100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondsToTimer(long miliSeconds){
        String timerString = "";
        String secondsString;

        int hours = (int)(miliSeconds / (1000*60*60));
        int minutes = (int)(miliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((miliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }
}
