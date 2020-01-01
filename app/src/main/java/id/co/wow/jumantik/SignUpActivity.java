package id.co.wow.jumantik;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class SignUpActivity extends AppCompatActivity {


    EditText nama, namap, telp, alamat;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    final String TAG_SUKSES = "sukses";
    int sukses, suksesupdate;
    String s_telp, s_namap, pass, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nama = (EditText) findViewById(R.id.edit_nama_signup);
        namap = (EditText) findViewById(R.id.edit_namap_signup);
        telp = (EditText) findViewById(R.id.edit_notelp_signup);
        alamat = (EditText) findViewById(R.id.edit_alamat_signup);
        jsonParser = new JSONParser();
       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
    }

    public void submit(View view){
        if(nama.getText().toString().trim().equals("")){
            nama.setError("Kolom ini harus diisi");
        } else if(namap.getText().toString().trim().equals("")){
            namap.setError("Kolom ini harus diisi");
        }else if(telp.getText().toString().trim().equals("")){
            telp.setError("Kolom ini harus diisi");
        }else if(alamat.getText().toString().trim().equals("")){
            alamat.setError("Kolom ini harus diisi");
        }else{
            new Registrasi().execute();
        }


    }

    public void progressDialog (String pesan, Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(pesan);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void alertDialog(String judul, String pesan, String tulisanbutton, Context context){
        new AlertDialog.Builder(context)
                .setTitle(judul)
                .setMessage(pesan)
                .setNeutralButton(tulisanbutton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }})
                .show();
    }

    public void sendSms(String telp) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                telp,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                            pass = "" + phoneAuthCredential.getSmsCode();
                            new UpdatePass().execute();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {


                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);


                    }
                });
    }

    class UpdatePass extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("telp", s_telp));
                params.add(new BasicNameValuePair("pass", pass));
                String path = jsonParser.getIP() + "update_pass_user.php";
                JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                suksesupdate = jsonObject.getInt(TAG_SUKSES);
                error = jsonObject.getString("error");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            if(suksesupdate==1){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Toast.makeText(SignUpActivity.this, hasil, Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("BERHASIL")
                                .setMessage("Kamu berhasil mendaftar di jumantik.\n Silahkan kamu cek SMS untuk mendapatkan password")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                        finish();
                                    }})
                                .show();


                    }
                });
            }else{
                alertDialog("Gagal",
                        error,
                        "OK", SignUpActivity.this);
            }
        }
    }


    class Registrasi extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog("Proses Pendaftaran", SignUpActivity.this);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            try {

                String s_nama = nama.getText().toString().trim();
                s_namap = namap.getText().toString().trim();
                s_telp = telp.getText().toString().trim();
                String s_alamat = alamat.getText().toString().trim();
                //pass = "" + new Random().nextInt(999999);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nama", s_nama));
                params.add(new BasicNameValuePair("namap", s_namap));
                params.add(new BasicNameValuePair("telp", s_telp));
                params.add(new BasicNameValuePair("alamat", s_alamat));
                params.add(new BasicNameValuePair("pass", "000000"));
                String path = jsonParser.getIP() + "insert_user.php";
                JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                sukses = jsonObject.getInt(TAG_SUKSES);
                error = jsonObject.getString("error");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(sukses == 1){
                String stelp = telp.getText().toString().trim();
                String newnotelp = "+62" + stelp.substring(1);
                sendSms(newnotelp);
                //new UpdatePass().execute();

            }else{
                pDialog.dismiss();
                alertDialog("Gagal",
                        "Yahh!!! mohon maaf kamu gagal mendaftar. sepertinya kamu sudah terdaftar deh di aplikasi ini " + error,
                        "OK", SignUpActivity.this);
            }

        }
    }
}
