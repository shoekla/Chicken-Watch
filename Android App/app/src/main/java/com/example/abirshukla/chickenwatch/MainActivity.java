package com.example.abirshukla.chickenwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";

    /**
     * @return True if the external storage is available. False otherwise.
     */
    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * @return True if the external storage is writable. False otherwise.
     */
    public static boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }
    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = MainActivity.this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref != null) {
            int sec = sharedPref.getInt("sec",-1);
            System.out.println("Abir: D: "+sec);
            if (sec != -1) {
                if (SettingUser.edit) {
                    SettingUser.setWarn(sec);
                    SettingUser.edit = false;
                }
            }

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> mp = getAllMedia();
        final ArrayList<String> names = new ArrayList<>();
        for (String sa: mp) {
           names.add(sa.substring(sa.lastIndexOf("/")+1));
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,names);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent v = new Intent(MainActivity.this,VideoMenu1.class);
                v.putExtra("fileName", mp.get(position));
                v.putExtra("videoName",names.get(position));
                startActivity(v);
                //Toast.makeText(MainActivity.this, mp.get(position), Toast.LENGTH_LONG).show();


            }
        });

        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(s);
    }
    public void set(View view) {
        Intent s = new Intent(MainActivity.this,Settings.class);
        startActivity(s);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sec",SettingUser.warn);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("sec",SettingUser.warn);
        editor.commit();
        super.onDestroy();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int sec = savedInstanceState.getInt("sec");
        SettingUser.setWarn(sec);
    }
}
