package info.suvaya.onestock.app;


import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkRunnable implements Runnable {
    INewsLoader newsLoader;
    String urlString;

    public NetworkRunnable(String urlString, INewsLoader newsLoader) {
        this.urlString = urlString;
        this.newsLoader = newsLoader;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            InputStream stream = conn.getInputStream();

            newsLoader.loadNews(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
