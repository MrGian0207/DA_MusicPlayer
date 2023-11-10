package com.example.da_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.da_musicplayer.Define.Song;
import com.example.da_musicplayer.Define.Songs_Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerActivity extends AppCompatActivity {
    ImageButton btn_PlayPause;
    ImageButton btn_prev;
    ImageButton btn_next;
    ImageButton btn_looping;
    ImageButton btn_playlist;
    CircleImageView image_player;
    TextView textCurrentTime, textTotalDuration;
    SeekBar playerSeekbar;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    Intent intent_songofalbum;
    Intent intent_songofartist;
    Intent intent_TopSongs;
    Intent intent_song_FromSearchInAlbum;
    Intent intent_song_FromFavouriteList;
    ArrayList<Songs_Item> songsList = new ArrayList<>();

    LinearLayout backFromPlayerActivity;

    protected String linkSong;
    int position;
    boolean isLooping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        isLooping = false;
        backFromPlayerActivity = findViewById(R.id.backFromPlayerActivity);
        image_player = findViewById(R.id.image_player);
        btn_PlayPause = findViewById(R.id.btn_play);
        btn_prev = findViewById(R.id.btn_precious);
        btn_next = findViewById(R.id.btn_next);
        btn_looping = findViewById(R.id.btn_loopingSong);
        btn_playlist = findViewById(R.id.btn_playlist);
        textCurrentTime = findViewById(R.id.textCurrentTime);
        textTotalDuration = findViewById(R.id.textTotalDuration);
        playerSeekbar = findViewById(R.id.playerSeekbar);
        mediaPlayer = new MediaPlayer();
///////////////////// btn_playlist /////////////////////////////////////////////////////////////////
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songsList.size()!=0) {
                    onBackPressed(PlayerActivity.this);
                }
            }
        });
//////////////////// btn_looping /////////////////////////////////////////////////////////////////
        btn_looping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isLooping == false){
                   isLooping = true;
                   prepareMediaPlayer();
                   mediaPlayer.setLooping(true);
                   mediaPlayer.start();
                   btn_looping.setBackgroundResource(R.drawable.baseline_repeat_black_24);

               } else {
                   isLooping = false;
                   mediaPlayer.setLooping(false);
                   btn_looping.setBackgroundResource(R.drawable.baseline_repeat_24);
               }

            }
        });
///////////////////// backFromPlayerActivity //////////////////////////////////////////////////////
        backFromPlayerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onMoveToSongList(PlayerActivity.this);
            }
        });
////////////////////// Get nhạc từ TopSongs//////////////////////////////////////////////////
        intent_TopSongs = getIntent();
        Song top_song = (Song) intent_TopSongs.getSerializableExtra("song");
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
 //////////////////// Get nhạc từ trong từng artist of TopArtist
        intent_songofartist = getIntent();
        ArrayList<Songs_Item> songs_of_artist_list = (ArrayList<Songs_Item>) intent_songofartist.getSerializableExtra("songs_artist_list");
        position = (int) intent_songofartist.getIntExtra("position_Artist",-1);
        if(songs_of_artist_list != null & position >= 0) {
            Picasso.get()
                    .load(songs_of_artist_list.get(position).getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = songs_of_artist_list.get(position).getLink_song();
        }
///////////////////// Get nhạc từ trong từng album of TopAlbums
        intent_songofalbum = getIntent();
        ArrayList<Songs_Item> songs_of_album_list = (ArrayList<Songs_Item>) intent_songofalbum.getSerializableExtra("songs_album_list");
        position = (int) intent_songofalbum.getIntExtra("position_Album",-1);
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
////////////////// Get nhạc từ favourite song list //////////////////////////////////////////////////////
        intent_song_FromFavouriteList = getIntent();
        ArrayList<Songs_Item> songs_of_favouriteList = (ArrayList<Songs_Item>) intent_song_FromFavouriteList.getSerializableExtra("favouriteSong_list");
        position = (int) intent_songofalbum.getIntExtra("position_song_favourite",-1);
        if (songs_of_favouriteList != null && position >= 0){
            Picasso.get()
                    .load(songs_of_favouriteList.get(position).getSource_photo())
                    .resize(2000, 2000)
                    .centerCrop()
                    .into(image_player);
            linkSong = songs_of_favouriteList.get(position).getLink_song();
        }

///////////////////// Get list nhạc tồn tại//////////////////////////////////////////////////////////////
        if (songs_of_album_list != null){
            songsList = songs_of_album_list;
        } else if (songs_fromSearchInAlbum != null) {
            songsList = songs_fromSearchInAlbum;
        } else if (songs_of_favouriteList != null) {
            songsList = songs_of_favouriteList;
        } else if (songs_of_artist_list != null) {
            songsList = songs_of_artist_list;
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
////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekbar.setSecondaryProgress(percent);
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop(); // Dừng phát nhạc
            }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void prepareMediaPlayer() {
        try{
            mediaPlayer.setDataSource(linkSong);
            mediaPlayer.prepare();
            textTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        } catch (Exception exception){
//            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()){
            playerSeekbar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())*100));
            handler.postDelayed(updater, 1000);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
    // Trong hoạt động mới của bạn
    public void onBackPressed(Context context) {
        super.onBackPressed();
        // Gọi phương thức navigateToSearchFragment() để quay lại SearchFragment
        MainActivity mainActivity = new MainActivity();
        // Kiểm tra xem mediaPlayer đang phát nhạc hay không
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop(); // Dừng phát nhạc
        }
        // Tạo một đối tượng MainActivity
        mainActivity.navigateToFragment(context);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
    public void onMoveToSongList(Context context) {
        super.onBackPressed();
        AlbumActivity albumActivity = new AlbumActivity(); //
        albumActivity.navigateToAlbumActivity(context);
    }
}
