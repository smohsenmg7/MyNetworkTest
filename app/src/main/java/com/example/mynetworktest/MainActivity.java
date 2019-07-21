package com.example.mynetworktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        String tag = (String) view.getTag();
        switch (tag) {
            case "1":
                startActivity(new Intent(this, FirstExampleActivity.class));
                break;
            case "2":
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case "3":
                startActivity(new Intent(this, HttpParamsActivity.class));
            default:
                break;
        }

    }
}
