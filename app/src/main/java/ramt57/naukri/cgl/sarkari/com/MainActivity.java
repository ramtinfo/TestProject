package ramt57.naukri.cgl.sarkari.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ramt57.naukri.cgl.sarkari.com.adapter.HotAdapter;
import ramt57.naukri.cgl.sarkari.com.adapter.Marquee_Adapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "HTML";
    RecyclerView marquee_recycler, hot_recycler;
    Marquee_Adapter marquee_adapter;
    HotAdapter hotAdapter;
    ArrayList<String> hot_titles = new ArrayList<>();
    ArrayList<String> marquee_titles = new ArrayList<>();
    CardView syllabus, answer_key, latest_job, admit_card, result, about, certificate, important, admission;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_cardView();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        marquee_recycler = findViewById(R.id.recyclerview);
        hot_recycler = findViewById(R.id.hot_reylerView);
        hot_recycler.setLayoutManager(new LinearLayoutManager(this));
        marquee_recycler.setLayoutManager(new LinearLayoutManager(this));
        new RetrieveFeedTask().execute();
    }

    private void init_cardView() {
        syllabus = findViewById(R.id.syllabus);
        syllabus.setOnClickListener(this);
        latest_job = findViewById(R.id.latest);
        latest_job.setOnClickListener(this);
        answer_key = findViewById(R.id.answer);
        answer_key.setOnClickListener(this);
        result = findViewById(R.id.result);
        result.setOnClickListener(this);
        admit_card = findViewById(R.id.admit);
        admit_card.setOnClickListener(this);
        about = findViewById(R.id.about);
        about.setOnClickListener(this);
        admission = findViewById(R.id.admission);
        admission.setOnClickListener(this);
        certificate = findViewById(R.id.certificate);
        certificate.setOnClickListener(this);
        important = findViewById(R.id.important);
        important.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.syllabus:
                Intent intent=new Intent(this,DetailListActivity.class);
                intent.putExtra("TITLE","Syllabus");
                intent.putExtra("URL","http://www.sarkariresult.com/syllabus.php");
                startActivity(intent);
                break;
            case R.id.about:
                Intent intent1=new Intent(this,DetailListActivity.class);
                intent1.putExtra("TITLE","About");
                intent1.putExtra("URL","");
                startActivity(intent1);
                break;
            case R.id.admission:
                Intent intent2=new Intent(this,DetailListActivity.class);
                intent2.putExtra("TITLE","Admission");
                intent2.putExtra("URL","http://sarkariresult.com/admission.php");
                startActivity(intent2);
                break;
            case R.id.answer:
                Intent intent3=new Intent(this,DetailListActivity.class);
                intent3.putExtra("TITLE","Answer Key");
                intent3.putExtra("URL","http://www.sarkariresult.com/answerkey.php");
                startActivity(intent3);
                break;
            case R.id.latest:
                Intent intent4=new Intent(this,DetailListActivity.class);
                intent4.putExtra("TITLE","Latest Job");
                intent4.putExtra("URL","http://www.sarkariresult.com/latestjob.php");
                startActivity(intent4);
                break;
            case R.id.admit:
                Intent intent5=new Intent(this,DetailListActivity.class);
                intent5.putExtra("TITLE","Admit Card");
                intent5.putExtra("URL","http://www.sarkariresult.com/admitcard.php");
                startActivity(intent5);
                break;
            case R.id.certificate:
                Intent intent6=new Intent(this,DetailListActivity.class);
                intent6.putExtra("TITLE","Verify Certificate");
                intent6.putExtra("URL","");
                startActivity(intent6);
                break;
            case R.id.important:
                Intent intent7=new Intent(this,DetailListActivity.class);
                intent7.putExtra("TITLE","Important");
                intent7.putExtra("URL","");
                startActivity(intent7);
                break;
            case R.id.result:
                Intent intent8=new Intent(this,DetailListActivity.class);
                intent8.putExtra("TITLE","Result");
                intent8.putExtra("URL","http://www.sarkariresult.com/result.php");
                startActivity(intent8);
                break;
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Document> {

        public RetrieveFeedTask() {
            super();
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
            for (int i = 1; i <= 8; i++) {
                Elements tableparse = document.select("table #image" + i + " a");
                for (Element tr : tableparse) {
                    Log.w(TAG, tr.text() + " 2 " + tr.absUrl("href"));
                    hot_titles.add(tr.text().replace("Apply Online", ""));
                }
            }
            hotAdapter = new HotAdapter(hot_titles);
            hot_recycler.setAdapter(hotAdapter);
            Elements newsHeadlines = document.select("#marquee1 marquee a b");
            for (Element headline : newsHeadlines) {
//            log("%s\n\t%s",
//                    headline.attr("title"), headline.absUrl("href"));
                marquee_titles.add(headline.text());
                Log.d(TAG, headline.text() + " " + headline.absUrl("href"));
            }
            marquee_adapter = new Marquee_Adapter(marquee_titles);
            marquee_adapter.notifyDataSetChanged();
            marquee_recycler.setAdapter(marquee_adapter);
        }

        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.sarkariresult.com/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }
    }
}
