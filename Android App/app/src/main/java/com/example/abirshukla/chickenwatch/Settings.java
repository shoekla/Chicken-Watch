package com.example.abirshukla.chickenwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Current Number of Seconds: "+SettingUser.warn);

    }
    public void subSet(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        int sec = Integer.parseInt(editText.getText().toString());
        SettingUser.setWarn(sec);
        Intent b = new Intent(Settings.this,MainActivity.class);
        startActivity(b);
    }
}
