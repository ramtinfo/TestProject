package ramt57.naukri.cgl.sarkari.com;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ramt57.naukri.cgl.sarkari.com.adapter.HotAdapter;
import ramt57.naukri.cgl.sarkari.com.adapter.Marquee_Adapter;
import ramt57.naukri.cgl.sarkari.com.pojo.LinkPojo;

public class DetailListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static Marquee_Adapter marquee_adapter;
    ArrayList<LinkPojo> data = new ArrayList<>();
    private final String TAG = "HTML";
    ProgressDialog progressDialog;
    private FinestWebView.Builder finestWebView;
    MaterialSearchView searchView;
    public static ArrayList<LinkPojo> dictionaryWords;
    public static ArrayList<LinkPojo> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                marquee_adapter.getFilter().filter(newText.toString());
                return false;
            }
        });

//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });
        finestWebView = new FinestWebView.Builder(DetailListActivity.this);
        finestWebView.webViewSupportZoom(true);
//        finestWebView.webViewDisplayZoomControls(true);
        finestWebView.webViewBuiltInZoomControls(true);
        finestWebView.webViewAllowFileAccess(true);
        finestWebView.webViewAllowContentAccess(true);
        finestWebView.webViewJavaScriptEnabled(true);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredList = new ArrayList<LinkPojo>();
        marquee_adapter = new Marquee_Adapter(filteredList);
        marquee_adapter.setMarqueeclicklistner(new Marquee_Adapter.MarqueeClick() {
            @Override
            public void onClick(String URl) {
                finestWebView.show(URl);
            }
        });
        recyclerView.setAdapter(marquee_adapter);
        progressDialog = new ProgressDialog(this);
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
                    LinkPojo pojo = new LinkPojo();
                    pojo.setTitle(headline.text() + "");
                    pojo.setLink(headline.absUrl("href"));
                    data.add(pojo);
                }
            }
            dictionaryWords = data;

            filteredList.addAll(dictionaryWords);
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

    //    search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
