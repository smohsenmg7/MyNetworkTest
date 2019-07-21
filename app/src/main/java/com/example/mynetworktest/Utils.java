package com.example.mynetworktest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.mynetworktest.AsyncTaskActivity.URL;

public class Utils {

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        return stringBuilder.toString();
    }

    public static String getDataHttpClinet(String uri) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(uri);
        try {
            HttpResponse response = httpClient.execute(method);
            return Utils.inputStreamToString(response.getEntity().getContent());
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static String getDataUrlConnection(String uri) {
        try {
            URL url = new URL(uri);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                String result = inputStreamToString(httpURLConnection.getInputStream());
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class RequestData {
        private String uri = "";
        private String method = "GET";
        private Map<String, String> params;

        public RequestData(String uri, String method) {
            this.uri = uri;
            this.method = method;
            this.params = new HashMap<>();
        }

        public RequestData() {
            this("", "GET");
            this.params = new HashMap<>();
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        public void setParameters(String key, String value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            params.put(key, value);
        }
    }
}
