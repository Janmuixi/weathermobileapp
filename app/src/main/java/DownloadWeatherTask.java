import android.os.AsyncTask;

import java.net.URL;

class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    protected Long doInBackground(URL... url) {
        long temperatura = 0;
        return temperatura;
    }

    protected void onPostExecute(Long result) {
        System.out.print("Downloaded " + result + " bytes");
    }
}