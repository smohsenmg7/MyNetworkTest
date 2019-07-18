package com.example.mynetworktest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FirstExampleActivity extends AppCompatActivity {

    public static final String URL = "https://apianote.000webhostapp.com/select.php";
    static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_example);
        textView = findViewById(R.id.tv);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("GET");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Thread thread = new Thread(new Runnable() {

                    Handler handler = new Handler() {

                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String content = (String) msg.getData().get("content");
                            textView.setText(content);
                        }
                    };

                    @Override
                    public void run() {
                        String content = getData();
                        android.os.Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("content", content);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
                thread.start();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private String getData() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(method);
            return Utils.inputStreamToString(response.getEntity().getContent());
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
