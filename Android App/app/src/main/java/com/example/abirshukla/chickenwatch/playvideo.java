package com.example.abirshukla.chickenwatch;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class playvideo extends FragmentActivity {
    VideoView videoView;
    boolean con = true;
    int[] times;
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
        String[] timesS = b.getString("times").split(",");
        times = new int[timesS.length];
        System.out.println("Abir Data: int len:"+times.length);
        for (int i = 0; i < timesS.length; i++) {
            times[i] = Integer.parseInt(timesS[i]);
            System.out.println("Abir Data: "+times[i]);

        }

        Toast.makeText(playvideo.this, "Playing "+file, Toast.LENGTH_LONG).show();
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(file));
        videoView.start();
        for (int i = 0; i < timesS.length; i++) {
            times[i] = (videoView.getDuration()/1000) - Integer.parseInt(timesS[i]);
        }

        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {

                                                setDuration();
                                                timerCounter();
                                            }
                                        }

        );
    }

    private Timer timer;
    private void timerCounter(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int time = (duration - videoView.getCurrentPosition())/1000;
                        for (int i = 0; i < times.length;i++) {
                            int w = time - times[i];
                            System.out.println("Abir Data: w: "+w);
                            if (w < SettingUser.warn) {
                                Toast.makeText(playvideo.this, "Jump Scare in "+ w, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }

    private int duration = 0;

    private void setDuration(){
        duration = videoView.getDuration();
    }








}
