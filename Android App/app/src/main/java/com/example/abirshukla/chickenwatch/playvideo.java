package com.example.abirshukla.chickenwatch;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class playvideo extends FragmentActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
       requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_playvideo);
        //setContentView(R.layout.activity_playvideo);
        Bundle b = getIntent().getExtras();
        String file = b.getString("fileName");
        Toast.makeText(playvideo.this, "Playing "+file, Toast.LENGTH_LONG).show();
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(file));
        videoView.start();
        videoView.setMediaController(new MediaController(this));

    }






}
