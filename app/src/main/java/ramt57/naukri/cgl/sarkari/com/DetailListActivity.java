package ramt57.naukri.cgl.sarkari.com;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ramt57.naukri.cgl.sarkari.com.adapter.HotAdapter;
import ramt57.naukri.cgl.sarkari.com.adapter.Marquee_Adapter;

public class DetailListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private Marquee_Adapter marquee_adapter;
    ArrayList<String> data = new ArrayList<>();
    private final String TAG = "HTML";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marquee_adapter = new Marquee_Adapter(data);
        recyclerView.setAdapter(marquee_adapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        String title = getIntent().getStringExtra("TITLE");
        if (title != null && !title.isEmpty()) {
            try {
                getSupportActionBar().setTitle(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String URL = getIntent().getStringExtra("URL");
//        String URL="http://www.sarkariresult.com/result.php";
        if (URL != null && !URL.isEmpty()) {
            new RetrieveFeedTask(URL).execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RetrieveFeedTask extends AsyncTask<String, Void, Document> {
        String URL;

        public RetrieveFeedTask() {
            super();
        }

        public RetrieveFeedTask(String URL) {
            this.URL = URL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            progressDialog.dismiss();
            Elements newsHeadlines = document.select("#post ul li a");
            for (Element headline : newsHeadlines) {
                if (headline.text() != null && !headline.text().isEmpty()) {
                    Log.d(TAG + "d", headline.text() + " : " + headline.absUrl("href"));
                    data.add(headline.text() + "");
                }
            }
            marquee_adapter.notifyDataSetChanged();
        }

        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }
    }
}
