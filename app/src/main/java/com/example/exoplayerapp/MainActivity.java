package com.example.exoplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
//    private String music_url = "https://opengameart.org/sites/default/files/Cyberpunk%20Moonlight%20Sonata_0.mp3";

    private ProgressBar mProgressBar;

    private Player.EventListener mEventListener;

    private PermissionToken permissionToken;
    private FloatingActionButton fab;

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
        mProgressBar = findViewById(R.id.progress_bar);

        mSimpleExoPlayer  = new SimpleExoPlayer.Builder(this).build();

        mPlayerView.setPlayer(mSimpleExoPlayer);

        for (String music_url : music_list) {

            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(music_url));

            mSimpleExoPlayer.addMediaItem(mediaItem);

        }

      //  mSimpleExoPlayer.setMediaItem(mediaItem);
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();

        mEventListener = new Player.EventListener() {

            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_BUFFERING) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                else if (state == Player.STATE_READY) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        };

        mSimpleExoPlayer.addListener(mEventListener);

        fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setupPermissions();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSimpleExoPlayer != null) {

            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;

        }

    }

    private void setupPermissions() {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        // Download the current music

                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }

}