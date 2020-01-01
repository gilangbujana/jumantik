package id.co.wow.jumantik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
//import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class ProfilActivity extends AppCompatActivity {

    EditText edit_nama, edit_namap, edit_no_telp, edit_pass, edit_alamat, edit_rt, edit_rw, edit_kelurahan,
            edit_kecamatan, edit_kabupaten;
    TextView tv_namap_profil;
    JSONParser jsonParser;
    int sukses;
    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        edit_nama = (TextInputEditText) findViewById(R.id.edit_nama_profil);
        edit_namap = (TextInputEditText) findViewById(R.id.edit_namap_profil);
        edit_no_telp = (TextInputEditText) findViewById(R.id.edit_telp_profil);
        edit_pass = (TextInputEditText) findViewById(R.id.edit_pass_profil);
        edit_alamat = (TextInputEditText) findViewById(R.id.edit_alamat_profil);
        edit_rt = (TextInputEditText) findViewById(R.id.edit_rt_profil);
        edit_rw = (TextInputEditText) findViewById(R.id.edit_rw_profil);
        edit_kelurahan = (TextInputEditText) findViewById(R.id.edit_kelurahan_profil);
        edit_kecamatan = (TextInputEditText) findViewById(R.id.edit_kecamatan_profil);
        edit_kabupaten = (TextInputEditText) findViewById(R.id.edit_kabupaten_profil);
        tv_namap_profil = (TextView) findViewById(R.id.tv_namap_profil);
        jsonParser = new JSONParser();
        JSONObject response;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilActivity.this);
        String cekjson = sharedPreferences.getString("jsonDataUser", "");
        if(cekjson!=null){
            try {
                response = new JSONObject(cekjson);
                edit_nama.setText(response.getString("nama"));
                edit_namap.setText(response.getString("namap"));
                tv_namap_profil.setText(response.getString("namap"));
                edit_no_telp.setText(response.getString("telp"));
                edit_pass.setText(response.getString("pass"));
                edit_alamat.setText(response.getString("alamat"));
                edit_rt.setText(response.getString("rt"));
                edit_rw.setText(response.getString("rw"));
                edit_kelurahan.setText(response.getString("kelurahan"));
                edit_kecamatan.setText(response.getString("kecamatan"));
                edit_kabupaten.setText(response.getString("kabupaten"));
            } catch (JSONException e) {
                alert(e.getMessage(), "OKE", null);
            }
        }
    }

    public void perbaruiProfil(View view){
        if(!isEmptyEditText()){
            /*Alerter.create(ProfilActivity.this)
                    .setBackgroundColorRes(R.color.colorPrimaryDark)
                    .setTitle("APAKAH KAMU YAKIN INGIN PERBARUI PROFIL?")
                    .addButton("YA", R.style.AlertButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new UpdateData().execute();
                        }
                    })
                    .addButton("TIDAK", R.style.AlertButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Alerter.hide();
                        }
                    })
                    .show();*/
            new AlertDialog.Builder(this)
                    .setTitle("APAKAH KAMU YAKIN INGIN PERBARUI PROFIL?")
                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new UpdateData().execute();
                        }
                    })
                    .setNegativeButton("TIDAK", null)
                    .show();
        }
    }

    ProgressDialog pDialog;
    public void progress(String pesan, Boolean progress, Boolean dissmissable){
       /* Alerter.create(ProfilActivity.this)
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .setText(pesan.toUpperCase())
                .enableProgress(progress)
                .setDismissable(dissmissable)
                .show();*/
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(pesan);
        pDialog.setCancelable(dissmissable);
        pDialog.show();
    }

    public void alert(String pesan, String pos, String neg){
        new AlertDialog.Builder(this)
                .setTitle(pesan)
                .setPositiveButton(pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(neg, null)
                .show();
    }

    public Boolean isEmptyEditText(){
        if(edit_nama.getText().toString().equals("")){
            edit_nama.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_namap.getText().toString().equals("")){
            edit_namap.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_no_telp.getText().toString().equals("")){
            edit_no_telp.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_pass.getText().toString().equals("")){
            edit_pass.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_alamat.getText().toString().equals("")){
            edit_alamat.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_rt.getText().toString().equals("")){
            edit_rt.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_rw.getText().toString().equals("")){
            edit_rw.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_kelurahan.getText().toString().equals("")){
            edit_kecamatan.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_kecamatan.getText().toString().equals("")){
            edit_kecamatan.setError("Kolom ini harus diisi");
            return true;
        }else if(edit_kabupaten.getText().toString().equals("")){
            edit_kabupaten.setError("Kolom ini harus diisi");
            return true;
        }

        return false;
    }


    class UpdateData extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress("TUNGGU YA SEDANG PROSES PERBARUI PROFIL", true, false);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            try {
                String nama = edit_nama.getText().toString().trim();
                String namap = edit_namap.getText().toString().trim();
                String telp = edit_no_telp.getText().toString().trim();
                String pass = edit_pass.getText().toString().trim();
                String alamat = edit_alamat.getText().toString().trim();
                String rt = edit_rt.getText().toString().trim();
                String rw = edit_rw.getText().toString().trim();
                String kelurahan = edit_kelurahan.getText().toString().trim();
                String kecamatan = edit_kecamatan.getText().toString().trim();
                String kabupaten = edit_kabupaten.getText().toString().trim();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("nama", nama));
                params.add(new BasicNameValuePair("namap", namap));
                params.add(new BasicNameValuePair("telp", telp));
                params.add(new BasicNameValuePair("pass", pass));
                params.add(new BasicNameValuePair("alamat", alamat));
                params.add(new BasicNameValuePair("rt", rt));
                params.add(new BasicNameValuePair("rw", rw));
                params.add(new BasicNameValuePair("kelurahan", kelurahan));
                params.add(new BasicNameValuePair("kecamatan", kecamatan));
                params.add(new BasicNameValuePair("kabupaten", kabupaten));
                String path = jsonParser.getIP()+"user_update.php";
                JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                sukses = jsonObject.getInt("sukses");
                jsonData = jsonObject.getJSONObject("data");
                pesan = jsonObject.getString("pesan");
            } catch (JSONException e) {
                pesan = e.getMessage();
                //alerter(e.getMessage(), false, true);
            }


            return null;
        }

        String pesan="";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.hide();
            if(sukses==1){
                    SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(ProfilActivity.this);
                    SharedPreferences.Editor sEditor = sharedPreferences.edit();
                    sEditor.putString("jsonDataUser", jsonData.toString());
                    sEditor.commit();
                    sEditor.apply();
                    alert("PROFILMU BERHASIL DI PERBARUI", "OKE", null);
            }else{
                    alert(pesan, "OKE", null);
            }
        }
    }
}
