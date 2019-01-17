package e.jan.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncThread thread = new AsyncThread();

        thread.execute("https://www.google.com");

        webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

    }

    public class AsyncThread extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpsURLConnection connection = null;
            String result = "";
            try {

                url = new URL(strings[0]);
                connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                int data = inputStream.read();

                while (data != -1) {
                    result += (char) data;
                    data = inputStream.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("RESULT",result);
            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            webView.loadData(data,"text/html","UTF-8");
        }
    }
}
