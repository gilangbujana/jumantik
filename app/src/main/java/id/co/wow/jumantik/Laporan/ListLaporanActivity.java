package id.co.wow.jumantik.Laporan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;


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

public class ListLaporanActivity extends AppCompatActivity {


    ShimmerRecyclerView srv_list_laporan;
    PullRefreshLayout pullRefreshLayout;
    RiwayatLaporanAdapter riwayatLaporanAdapter;
    JSONArray jsonArray;
    JSONObject jsonObject;
    JSONParser jsonParser;
    String telp, pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_laporan);
        jsonParser = new JSONParser();
        srv_list_laporan = (ShimmerRecyclerView) findViewById(R.id.srv_list_laporan);
        srv_list_laporan.setLayoutManager(new LinearLayoutManager(ListLaporanActivity.this));
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout_list_laporan);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ListLaporanActivity.this);
        if(sp.contains("jsonDataUser")){
            try {
                jsonObject = new JSONObject(sp.getString("jsonDataUser", ""));
                telp = jsonObject.getString("telp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        new GetRiwayatLaporan().execute();

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetRiwayatLaporan().execute();
            }


        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetRiwayatLaporan().execute();
    }

    public void toLaporanActivity(View view){
        startActivity(new Intent(ListLaporanActivity.this, LaporanActivity.class));
    }

    class GetRiwayatLaporan extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pullRefreshLayout.setRefreshing(false);
            srv_list_laporan.showShimmer();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("telp", telp));
                String path = jsonParser.getIP() + "select_laporan_user.php";
                jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                pesan = jsonObject.getString("pesan");
                jsonArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pesan.equals("1")){
                riwayatLaporanAdapter = new RiwayatLaporanAdapter(jsonArray, ListLaporanActivity.this);
                srv_list_laporan.setAdapter(riwayatLaporanAdapter);
                riwayatLaporanAdapter.notifyDataSetChanged();
                srv_list_laporan.hideShimmer();

            }else{

            }
        }
    }

}
