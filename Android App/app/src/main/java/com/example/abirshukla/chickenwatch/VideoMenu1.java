package com.example.abirshukla.chickenwatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class VideoMenu1 extends AppCompatActivity {
    ProgressDialog progress;
    String filePath;
    String fileName;
    String videoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_menu1);
        Bundle b = getIntent().getExtras();
        filePath = b.getString("fileName");
        fileName = b.getString("videoName");
        videoName = fileName.substring(0,fileName.lastIndexOf(".")).toLowerCase();
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView2.setText("File Name: "+fileName);
        textView3.setText("Movie Found: "+videoName);
        progress = new ProgressDialog(this);
        progress.setMessage("Finding Chicken Details...");
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);


    }
    public void playReg(View view) {
        Intent v = new Intent(VideoMenu1.this,playVidReg.class);
        v.putExtra("fileName",filePath);
        startActivity(v);
    }
    public void chicken(View view) {
        progress.show();
        videoName = videoName.replace(" ","%20");
        String url = "https://chickenwatch.herokuapp.com/movieChicken/get%20out/";
        getHTML(url);
    }
    public void getHTML(final String url) {

        System.out.println("Begin HTML");
        System.out.println("Final Url: " + url);
        final String[] d = new String[1];
        Ion.with(getApplicationContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Intent c = new Intent(VideoMenu1.this,ChickenDetails.class);
                        System.out.println("Abir Data: result: "+result);
                        c.putExtra("result",result);
                        c.putExtra("fileName",filePath);
                        progress.hide();
                        progress.cancel();
                        startActivity(c);

                    }
                });
    }
}
