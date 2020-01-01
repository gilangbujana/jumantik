package id.co.wow.jumantik.Berita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;

import com.baoyz.widget.PullRefreshLayout;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import id.co.wow.jumantik.JSONParser;
import id.co.wow.jumantik.R;

public class ListBeritaActivity extends AppCompatActivity {


    ShimmerRecyclerView srv_berita;
    PullRefreshLayout prl_berita;
    ListBeritaAdapter list_berita_adapter;
    String pesan;
    JSONParser jsonParser;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_berita);
        jsonParser = new JSONParser();
        srv_berita = (ShimmerRecyclerView) findViewById(R.id.srv_list_berita);
        prl_berita = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout_list_berita);
        srv_berita.setLayoutManager(new LinearLayoutManager(ListBeritaActivity.this));
        new GetBerita().execute();

        prl_berita.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetBerita().execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetBerita().execute();
    }

    class GetBerita extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            srv_berita.showShimmer();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("all", "all"));
            String path = jsonParser.getIP() + "select_berita.php";
            JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
            try {
                jsonArray = jsonObject.getJSONArray("data");
                pesan = jsonObject.getString("pesan");
            } catch (JSONException e) {
                pesan = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pesan.equals("1")){
                list_berita_adapter = new ListBeritaAdapter(ListBeritaActivity.this, jsonArray);
                srv_berita.setAdapter(list_berita_adapter);
                list_berita_adapter.notifyDataSetChanged();
                srv_berita.hideShimmer();
            }
        }
    }
}
