package com.sns.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crxu.library.LogReport;
import com.crxu.library.util.JLog;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogReport.getInstance().upload(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).postDelayed(new Runnable() {
            @Override
            public void run() {
                JLog.e("TAG");
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                MainActivity.this.startActivity(intent);
            }
        },90000);
    }
}
