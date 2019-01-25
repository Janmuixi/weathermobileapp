package e.jan.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText ciudad;
    TextView tiempo;
    Button consultaBtn;

    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ciudad = findViewById(R.id.editText);
        tiempo = findViewById(R.id.textMain);
        consultaBtn = findViewById(R.id.button);
        consultaBtn.setText("Send >");
        tiempo.setText("");

        consultaBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsyncThread thread = new AsyncThread();
                String nombreCiudad = ciudad.getText().toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + nombreCiudad + "&appid=2ad918c0386450a61010cf6a0ac29ef4";
                thread.execute(url);
            }
        });

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
            return result;
        }

        @Override
        protected void onPostExecute(String data) {

            super.onPostExecute(data);
            JSONObject weatherObject = null;
            JSONObject main = null;
            double temp = 0;

            try {
                weatherObject = new JSONObject(data);
                main = weatherObject.getJSONObject("main");
                temp = main.getDouble("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView textView = (TextView) findViewById(R.id.textMain);
            textView.setText(String.valueOf(temp));
        }
    }
}
