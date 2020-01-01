package id.co.wow.jumantik.Laporan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
//import com.tapadoo.alerter.Alerter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import id.co.wow.jumantik.JSONParser;
import id.co.wow.jumantik.ProfilActivity;
import id.co.wow.jumantik.R;

public class LaporanActivity extends AppCompatActivity {

    Button btn_simpan_laporan;
    LaporanPagerAdapter laporanPagerAdapter;
    ArrayList<String> arrDR, arrLR;
    Bitmap bitmap;
    SharedPreferences sP;
    ArrayList<String> fotopath;
    int sukses;
    String keterangan;
    String hasil;
    JSONObject response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        btn_simpan_laporan = (Button) findViewById(R.id.btn_simpan_laporan);
        ViewPager viewPager = findViewById(R.id.laporan_viewpager);
        laporanPagerAdapter = new LaporanPagerAdapter(getSupportFragmentManager());
        keterangan = "";
        sP = PreferenceManager.getDefaultSharedPreferences(this);
        fotopath = new ArrayList<>();
        if(sP.getStringSet("fotopath", null) != null)
            fotopath.addAll(sP.getStringSet("fotopath", null));
        if(sP.getString("et_laporan", "")!=null)
            keterangan = sP.getString("et_laporan", "");
        viewPager.setAdapter(laporanPagerAdapter);
        TabLayout tabs = findViewById(R.id.laporan_tabs);
        tabs.setupWithViewPager(viewPager);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cekjson = sharedPreferences.getString("jsonDataUser", "");
        try {
            response = new JSONObject(cekjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btn_simpan_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrDR = ((DalamRumahFragment) laporanPagerAdapter.getItem(0)).adapter.getArrRadioButton();
                arrLR = ((LuarRumahFragment) laporanPagerAdapter.getItem(1)).adapter.getArrRadioButton();
                if (arrDR.contains(""))
                    alert("Lengkapi dulu ya pilihan laporannya baru nanti bisa di submit laporannya :)" /*+arrDR.toString()*/, "OKE", null);
                else if(arrLR.contains(""))
                    alert("iya yang di DALAM RUMAH laporannya sudah lengkap kok. tapi yang di luar rumah belum. lengkapi dulu ya!" /*+ arrLR.toString()*/, "OKE", null);
                else
                   /* Alerter.create(LaporanActivity.this)
                            .setBackgroundColorRes(R.color.colorPrimaryDark)
                            .setText("SUDAH YAKIN MAU DI KIRIM LAPORANNYA?".toUpperCase())
                            .addButton("YA", R.style.AlertButton, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Submit().execute();
                                }
                            })
                            .addButton("BELUM YAKIN", R.style.AlertButton, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Alerter.hide();
                                }
                            })
                            .enableInfiniteDuration(false)
                            .setDismissable(false)
                            .show(); */
                    new AlertDialog.Builder(LaporanActivity.this)
                            .setTitle("SUDAH YAKIN MAU DI KIRIM LAPORANNYA?".toUpperCase())
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new Submit().execute();
                                }
                            })
                            .setNegativeButton("BELUM YAKIN", null)
                            .show();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        fotopath = new ArrayList<>();
        if(sP.getStringSet("fotopath", null) != null)
            fotopath.addAll(sP.getStringSet("fotopath", null));
    }

    ProgressDialog pDialog;
    public void progress(String pesan, Boolean progress, Boolean dissmissable, Boolean infinite){
       /* Alerter.create(this)
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .setText(pesan.toUpperCase())
                .enableProgress(progress)
                .enableInfiniteDuration(infinite)
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    class Submit extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<>();
            int jml_jentikDR=0, jml_jentikLR=0;
            for(int i=0; i < arrDR.size(); i++){
                if(!arrDR.get(i).equals("3"))
                    jml_jentikDR++;
                if(!arrLR.get(i).equals("3")){
                    jml_jentikLR++;
                }
                params.add(new BasicNameValuePair("arrDR[]", arrDR.get(i)));
                params.add(new BasicNameValuePair("arrLR[]", arrLR.get(i)));
            }
            try {
                params.add(new BasicNameValuePair("no_telp", response.getString("telp")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params.add(new BasicNameValuePair("keterangan", sP.getString("et_laporan", "")));
            params.add(new BasicNameValuePair("jml_jentikDR", jml_jentikDR+""));
            params.add(new BasicNameValuePair("jml_jentikLR", ""+jml_jentikLR));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            params.add(new BasicNameValuePair("tanggal_laporan", df.format(new Date())));
            for(int i =0; i<fotopath.size(); i++){
                File file = new File(fotopath.get(i));
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if(bitmap!=null) {
                    params.add(new BasicNameValuePair("arrImageData[]", getStringImage(bitmap)));
                    params.add(new BasicNameValuePair("arrImageName[]", file.getName()));
                }
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = jsonParser.makeHttpRequest(jsonParser.getIP()+"upload.php", "POST", params);
            try {
                sukses = jsonObject.getInt("sukses");
                hasil = jsonObject.getString("pesan");

            } catch (JSONException e) {
                sukses = 0;
                hasil = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(sukses==1) {
                alert("LAPORAN BERHASIL DIKIRIM :)", "OK", null);
            }
            else
                alert("YAHHH LAPORAN GAGAL DIKIRIM :( COBA DEH PERIKSA JARINGAN INTERNET KAMU " + hasil, "OKE", null);

        }
    }
}
