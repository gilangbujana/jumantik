package id.co.wow.jumantik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

//import com.tapadoo.alerter.Alerter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class SignInActivity extends AppCompatActivity {

    EditText edit_phone;
    EditText edit_pass;
    JSONParser jsonParser;
    int sukses;
    JSONArray jsonArray;
    JSONObject jsonObject;
    JSONObject jsonDataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edit_phone = (EditText) findViewById(R.id.edit_notelp_login);
        edit_pass = (EditText) findViewById(R.id.edit_pass_login);
        jsonParser = new JSONParser();

    }

    ProgressDialog pDialog;
    public void progress(Context context, String pesan, Boolean dissmissable){
        /*Alerter.create(SignInActivity.this)
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .setText(pesan.toUpperCase())
                .enableProgress(progress)
                .setDismissable(dissmissable)
                .show();*/

        pDialog = new ProgressDialog(context);
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


    public void Login(View view){
        if(edit_phone.getText().toString().trim().equals("")){
            edit_phone.setError("Kolom ini harus diisi");
        } else if(edit_pass.getText().toString().trim().equals("")){
            edit_pass.setError("Kolom ini harus diisi");
        }else{
            new Login().execute();
        }
    }

    public void toSignUpActivity(View view){
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    class Login extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress(SignInActivity.this,"Tunggu Proses login ya", false);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            try {
                String telp = edit_phone.getText().toString().trim();
                String pass = edit_pass.getText().toString().trim();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("telp", telp));
                params.add(new BasicNameValuePair("pass", pass));
                String path = jsonParser.getIP()+"user_login.php";
                jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                sukses = jsonObject.getInt("sukses");
                jsonArray = jsonObject.getJSONArray("record");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.hide();
            //Alerter.hide();
            if(sukses == 1){
                try {
                    jsonDataUser= jsonObject.getJSONObject("data");
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
                    SharedPreferences.Editor sEditor = sharedPreferences.edit();
                    sEditor.putString("jsonDataUser", jsonDataUser.toString());
                    sEditor.apply();

                    alert("Kamu berhasil Login", "OK", null);
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                try {
                    String pesan = jsonObject.getString("pesan");
                    alert(pesan, "OKE", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
