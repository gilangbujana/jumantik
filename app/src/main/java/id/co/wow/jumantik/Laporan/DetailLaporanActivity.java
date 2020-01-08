package id.co.wow.jumantik.Laporan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartown.tableview.library.TableView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import id.co.wow.jumantik.JSONParser;
import id.co.wow.jumantik.R;

//import de.codecrafters.tableview.TableView;

public class DetailLaporanActivity extends AppCompatActivity {

    private TextView tv_verifikasi_detail;
    private TextView tv_ket_laporan_detail;
    private TextView tv_tgl_laporan_detail;
    private TextView tv_label_identitas;
    private TableView ltv_dr, ltv_lr, ltv_identitas;
    private Button btn_verifikasi;
    private JSONObject jsonObject, jsonUser, jsonResponse;
    private FotoAdapter fotoAdapter;
    private RecyclerView rv_listfoto;
    private String status, telp, id_laporan, pesan;
    private JSONParser jsonParser;
    private int statusResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        jsonParser = new JSONParser();
        tv_verifikasi_detail = findViewById(R.id.tv_verfikasi_detail);
        tv_ket_laporan_detail = findViewById(R.id.tv_ket_laporan_detail);
        tv_tgl_laporan_detail = findViewById(R.id.tv_tgl_laporan_detail);
        tv_label_identitas = findViewById(R.id.tv_label_identitas);
        rv_listfoto = findViewById(R.id.rv_foto_detail);
        btn_verifikasi = findViewById(R.id.btn_verifikasi_detail);


       ltv_dr = findViewById(R.id.table_dr_detail);
       ltv_lr= findViewById(R.id.table_lr_detail);
       ltv_identitas = findViewById(R.id.table_identitas_warga);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(DetailLaporanActivity.this);
        if(sp.contains("jsonDataUser")){
            try {
                jsonUser = new JSONObject(sp.getString("jsonDataUser", ""));
                status = jsonUser.getString("status");
                telp = jsonUser.getString("telp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {

            jsonObject = new JSONObject(getIntent().getStringExtra("detailLaporan"));
            id_laporan = jsonObject.getString("id_laporan");
            if(status.equalsIgnoreCase("kader")){
                JSONObject jsonIdentitas = jsonObject.getJSONObject("identitas");
                ltv_identitas
                        .clearTableContents()
                        .addContent("No Telp", jsonIdentitas.getString("telp"))
                        .addContent("Nama", jsonIdentitas.getString("nama"))
                        .addContent("Alamat", jsonIdentitas.getString("alamat"))
                        .addContent("RT/RW", jsonIdentitas.getString("rt")+"/"+jsonIdentitas.getString("rw"))
                        .addContent("Kelurahan", jsonIdentitas.getString("kelurahan"))
                        .addContent("Kecamatan", jsonIdentitas.getString("kecamatan"))
                        .addContent("Kabupaten", jsonIdentitas.getString("kabupaten"))
                        .refreshTable();
                tv_label_identitas.setVisibility(View.VISIBLE);
                ltv_identitas.setVisibility(View.VISIBLE);
                btn_verifikasi.setVisibility(View.VISIBLE);
            }


            ltv_dr.clearTableContents()
                    .setHeader("Jenis TPN", "Jentik Aedes", "Jentik Non Aedes", "Tidak Ada Jentik");

            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();

                    if(key.equals("jumlah_jentik_dr")){
                        ltv_dr.addContent("JUMLAH JENTIK", ""+jsonObject.getInt(key));
                    } else if(!key.equals("id_laporan") &&
                            !key.equals("tgl_laporan") &&
                            !key.equals("type") &&
                            !key.equals("keterangan") &&
                            !key.equals("status") &&
                            !key.equals("no_telp_kader") &&
                            !key.equals("foto") &&
                            !key.equals("no_telp") &&
                            !key.equals("identitas")){
                        int size = key.split("_").length;
                        if(!key.split("_")[size-1].equals("lr"))
                            if(key.split("_").length == 3){
                                String[] keyArr = key.split("_");
                                ltv_dr.addContent(keyArr[0].toUpperCase() + " " + keyArr[1].toUpperCase(),
                                        (jsonObject.getInt(key) == 1) ? "YA" : " ",
                                        (jsonObject.getInt(key) == 2) ? "YA" : " ",
                                        (jsonObject.getInt(key) == 3) ? "YA" : " ");
                            }else{
                                String[] keyArr = key.split("_");
                                ltv_dr.addContent(keyArr[0].toUpperCase(),
                                        (jsonObject.getInt(key) == 1) ? "YA" : " ",
                                        (jsonObject.getInt(key) == 2) ? "YA" : " ",
                                        (jsonObject.getInt(key) == 3) ? "YA" : " ");
                        }
                    }

            }

            ltv_dr.refreshTable();


            ltv_lr.clearTableContents()
                    .setHeader("Jenis TPN", "Jentik Aedes", "Jentik Non Aedes", "Tidak Ada Jentik");

            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();

                if(key.equals("jumlah_jentik_lr")){
                    ltv_lr.addContent("JUMLAH JENTIK", ""+jsonObject.getInt(key));
                } else if(!key.equals("id_laporan") &&
                        !key.equals("tgl_laporan") &&
                        !key.equals("type") &&
                        !key.equals("keterangan") &&
                        !key.equals("status") &&
                        !key.equals("no_telp_kader") &&
                        !key.equals("foto") &&
                        !key.equals("no_telp") &&
                        !key.equals("identitas")){
                    int size = key.split("_").length;
                    if(!key.split("_")[size-1].equals("dr"))
                        if(key.split("_").length == 3){
                            String[] keyArr = key.split("_");
                            ltv_lr.addContent(keyArr[0].toUpperCase() + " " + keyArr[1].toUpperCase(),
                                    (jsonObject.getInt(key) == 1) ? "YA" : " ",
                                    (jsonObject.getInt(key) == 2) ? "YA" : " ",
                                    (jsonObject.getInt(key) == 3) ? "YA" : " ");
                        }else{
                            String[] keyArr = key.split("_");
                            ltv_lr.addContent(keyArr[0].toUpperCase(),
                                    (jsonObject.getInt(key) == 1) ? "YA" : " ",
                                    (jsonObject.getInt(key) == 2) ? "YA" : " ",
                                    (jsonObject.getInt(key) == 3) ? "YA" : " ");
                        }
                }

            }

            ltv_lr.refreshTable();

            fotoAdapter = new FotoAdapter( jsonObject.getJSONArray("foto"), this);
            rv_listfoto.setHasFixedSize(true);
            rv_listfoto.setLayoutManager(new GridLayoutManager(this, 2));
            rv_listfoto.setAdapter(fotoAdapter);

            tv_tgl_laporan_detail.setText(jsonObject.getString("tgl_laporan"));
            tv_ket_laporan_detail.setText(jsonObject.getString("keterangan"));

            int statusLaporan = jsonObject.getInt("status");
            if(statusLaporan==1){
                tv_verifikasi_detail.setBackgroundResource(R.drawable.verified);
                tv_verifikasi_detail.setText("SUDAH DIVERIFIKASI");
                btn_verifikasi.setVisibility(View.GONE);
            }else{
                tv_verifikasi_detail.setBackgroundResource(R.drawable.unverified);
                tv_verifikasi_detail.setText("BELUM DIVERIFIKASI");
            }

            //Toast.makeText(this, getIntent().getStringExtra("detailLaporan"), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    ProgressDialog pDialog;
    public void progress(String pesan, Boolean dissmissable){

        pDialog = new ProgressDialog(this);
        pDialog.setTitle(pesan);
        pDialog.setCancelable(dissmissable);
        pDialog.show();
    }

    public void alert(String pesan, String pos){
        new AlertDialog.Builder(this)
                .setTitle(pesan)
                .setPositiveButton(pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public void onVerifikasiButtonClick(View view){
        new AlertDialog.Builder(this)
                .setTitle("Kader yakin ingin verifikasi laporan ini?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new VerifikasiLaporan().execute();
                    }
                })
                .setNegativeButton("TIDAK", null)
                .show();
    }


    class VerifikasiLaporan extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress("Tunggu ya sedang proses verifikasi", false);

        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("no_telp_kader", telp));
                params.add(new BasicNameValuePair("id_laporan", id_laporan));
                String path = jsonParser.getIP()+"verifikasi_laporan.php";
                jsonResponse = jsonParser.makeHttpRequest(path, "POST", params);
                statusResponse = jsonResponse.getInt("status");
                pesan = jsonResponse.getString("pesan");
            }catch (JSONException e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.hide();
            if(statusResponse==1){
                finish();
            }else{
                alert("Maaf :(, proses Verifikasi gagal!\n" + pesan, "OKE");
            }
        }
    }
}
