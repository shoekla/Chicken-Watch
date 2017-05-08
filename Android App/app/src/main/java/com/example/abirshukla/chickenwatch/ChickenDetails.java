package com.example.abirshukla.chickenwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChickenDetails extends AppCompatActivity {
    String filePath;
    ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_details);
        Bundle b = getIntent().getExtras();
        String[] arr = b.getString("result").split(",");
        filePath = b.getString("fileName");
        System.out.println("Abir Data: res: "+ b.getString("result"));
        names = new ArrayList<>();
        for (int i = 2; i < arr.length;i++) {
            names.add(arr[i]);
        }
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText("Found "+arr[0]+""+arr[1]);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,names);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

    }
    public void playReg(View view) {
        Intent v = new Intent(ChickenDetails.this,playVidReg.class);
        v.putExtra("fileName",filePath);
        startActivity(v);
    }
    public void chickenWatch(View view) {
        String times = "";
        for (int i = 0; i < names.size();i++) {
            String[] a = names.get(i).split(":");
            if (a.length == 1) {
                int time = Integer.parseInt(a[0]);
                times = times+time+",";
            }
            else if (a.length == 2){
                int time = Integer.parseInt(a[0])*60+Integer.parseInt(a[1]);
                times = times+time+",";
            }
            else {
                int time = Integer.parseInt(a[0])*3600+Integer.parseInt(a[1])*60+Integer.parseInt(a[2]);
                times = times+time+",";
            }
        }
        Intent p = new Intent(ChickenDetails.this,playvideo.class);
        p.putExtra("fileName",filePath);
        p.putExtra("times",times.substring(0,times.length()-1));
        startActivity(p);
    }


}
