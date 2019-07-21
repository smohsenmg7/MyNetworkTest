package com.example.mynetworktest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class AsyncTaskActivity extends AppCompatActivity {
    public static final String URL = "https://apianote.000webhostapp.com/select.php";

    TextView textView;

    ProgressBar progressBar;
    Button button,getData,url_Button;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        textView = findViewById(R.id.tv_async_task);
        textView.setMovementMethod(new ScrollingMovementMethod());
        button = findViewById(R.id.button_async_task);
        progressBar = findViewById(R.id.progressBar);
        getData=findViewById(R.id.button_async_task2);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskGetData taskGetData=new TaskGetData();
                taskGetData.execute(URL);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskIntro taskIntro = new TaskIntro("task#" + (++counter));
                taskIntro.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "bank", "uni", "car");
            }
        });
    }

    public class TaskIntro extends AsyncTask<String, String, String> {
        String taskName = "";

        public TaskIntro(String taskName) {
            this.taskName = taskName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            textView.append("<" + taskName + ">" + "starting task...\n");
        }

        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("work with :" + p);
            }
            return "done";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.append("<" + taskName + ">" + values[0] + "\n");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.append(result + "\n");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public class TaskGetData extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return Utils.getDataUrlConnection(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.append(s);
        }
    }

}
