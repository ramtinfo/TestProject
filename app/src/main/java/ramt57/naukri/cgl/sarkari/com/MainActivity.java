package ramt57.naukri.cgl.sarkari.com;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG="HTML";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrieveFeedTask().execute();
    }
    class RetrieveFeedTask extends AsyncTask<String, Void, Document> {

        public RetrieveFeedTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
//            TextView textView=findViewById(R.id.title);
//            textView.setText(document.title());
            Elements newsHeadlines = document.select("#menu ul li a");
            for (Element headline : newsHeadlines) {
//            log("%s\n\t%s",
//                    headline.attr("title"), headline.absUrl("href"));
                Log.d(TAG, headline.text()+" "+headline.absUrl("href"));
            }
        }

        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.sarkariresult.com/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  doc;
        }
    }
}
