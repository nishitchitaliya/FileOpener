package com.praharsh.vudit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity {
    private VideoView vw;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.video_player);
        vw = findViewById(R.id.videoView);
        vw.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                hideStatusBar();
            }
        });
        progressDialog = new ProgressDialog(this);
        showProgressBar();
        MediaController mc = new MediaController(this);
        mc.setAnchorView(vw);
        mc.setMediaPlayer(vw);
        Uri video = null;
        try {
            video = Uri.parse(getIntent().getStringExtra("file"));
        } catch (Exception e) {
            finish();
        }
        vw.setVideoURI(video);
        vw.setMediaController(mc);
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
            }
        });
        vw.requestFocus();
        vw.start();
    }

    @Override
    protected void onStop() {
        if (vw != null) vw.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (vw != null) vw.stopPlayback();
        super.onDestroy();
    }

    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    protected void showProgressBar() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
