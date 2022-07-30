package com.example.exoplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
//    private String music_url = "https://opengameart.org/sites/default/files/Cyberpunk%20Moonlight%20Sonata_0.mp3";

    private String[] music_list = new String[] {

            "https://opengameart.org/sites/default/files/song18_0.mp3",
            "https://opengameart.org/sites/default/files/TownTheme.mp3",
            "https://opengameart.org/sites/default/files/battleThemeA.mp3",
            "https://opengameart.org/sites/default/files/Heroic%20Demise%20%28New%29_0.mp3",
            "https://opengameart.org/sites/default/files/the_field_of_dreams.mp3"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayerView = findViewById(R.id.my_player_view);

        mSimpleExoPlayer  = new SimpleExoPlayer.Builder(this).build();

        mPlayerView.setPlayer(mSimpleExoPlayer);

        for (String music_url : music_list) {

            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(music_url));

            mSimpleExoPlayer.addMediaItem(mediaItem);

        }

      //  mSimpleExoPlayer.setMediaItem(mediaItem);
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSimpleExoPlayer != null) {

            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;

        }

    }
}