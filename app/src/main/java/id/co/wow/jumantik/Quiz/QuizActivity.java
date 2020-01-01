package id.co.wow.jumantik.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import id.co.wow.jumantik.JSONParser;
import id.co.wow.jumantik.R;
import id.co.wow.jumantik.ScoreActivity;

public class QuizActivity extends AppCompatActivity {

    RadioGroup rg_soal_1, rg_soal_3, rg_soal_4, rg_soal_5, rg_soal_6, rg_soal_7
            , rg_soal_8, rg_soal_9, rg_soal_10, rg_soal_11, rg_soal_12, rg_soal_13
            , rg_soal_14, rg_soal_17;

    CheckBox cb_a_2, cb_b_2, cb_e_2, cb_a_15, cb_b_15, cb_c_15, cb_d_15, cb_e_15
            , cb_a_16, cb_b_16, cb_c_16, cb_d_16, cb_e_16;

    Button btn_submit;
    int skor;
    String telp, tgl, pesan;
    JSONParser jsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        jsonParser = new JSONParser();
        skor=0;
        btn_submit = (Button) findViewById(R.id.btn_submit_quiz);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(QuizActivity.this);
        try {
            telp = new JSONObject(sp.getString("jsonDataUser", "")).getString("telp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rg_soal_1 = (RadioGroup) findViewById(R.id.rg_soal_1);
        rg_soal_3 = (RadioGroup) findViewById(R.id.rg_soal_3);
        rg_soal_4 = (RadioGroup) findViewById(R.id.rg_soal_4);
        rg_soal_5 = (RadioGroup) findViewById(R.id.rg_soal_5);
        rg_soal_6 = (RadioGroup) findViewById(R.id.rg_soal_6);
        rg_soal_7 = (RadioGroup) findViewById(R.id.rg_soal_7);
        rg_soal_8 = (RadioGroup) findViewById(R.id.rg_soal_8);
        rg_soal_9 = (RadioGroup) findViewById(R.id.rg_soal_9);
        rg_soal_10 = (RadioGroup) findViewById(R.id.rg_soal_10);
        rg_soal_11 = (RadioGroup) findViewById(R.id.rg_soal_11);
        rg_soal_12 = (RadioGroup) findViewById(R.id.rg_soal_12);
        rg_soal_13 = (RadioGroup) findViewById(R.id.rg_soal_13);
        rg_soal_14 = (RadioGroup) findViewById(R.id.rg_soal_14);
       // rg_soal_17 = (RadioGroup) findViewById(R.id.rg_soal_17);
        cb_a_2 = (CheckBox) findViewById(R.id.a_2);
        cb_b_2 = (CheckBox) findViewById(R.id.b_2);
        cb_e_2 = (CheckBox) findViewById(R.id.e_2);
        cb_a_15 = (CheckBox) findViewById(R.id.a_15);
        cb_b_15 = (CheckBox) findViewById(R.id.b_15);
        cb_c_15 = (CheckBox) findViewById(R.id.c_15);
        cb_d_15 = (CheckBox) findViewById(R.id.d_15);
        cb_e_15 = (CheckBox) findViewById(R.id.e_15);
        cb_a_16 = (CheckBox) findViewById(R.id.a_16);
        cb_b_16 = (CheckBox) findViewById(R.id.b_16);
        cb_c_16 = (CheckBox) findViewById(R.id.c_16);
        cb_d_16 = (CheckBox) findViewById(R.id.d_16);
        cb_e_16 = (CheckBox) findViewById(R.id.e_16);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(QuizActivity.this)
                        .setTitle("QUIZ")
                        .setMessage("APAKAH KAMU YAKIN INGIN SUBMIT? SUDAH DIISI SEMUA SOALNYA?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cekScore();
                                new InsertScore().execute();

                            }
                        })
                        .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });



    }


    class InsertScore extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("telp", telp));
            params.add(new BasicNameValuePair("score", skor+""));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            params.add(new BasicNameValuePair("tgl", sdf.format(new Date())));
            String path = jsonParser.getIP() + "insert_quiz.php";
            JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
            try {
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
                Intent intent = new Intent(QuizActivity.this, ScoreActivity.class);
                intent.putExtra("score", "" + skor);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(QuizActivity.this, pesan, Toast.LENGTH_SHORT).show();
            }
        }
    }


    void cekScore(){
        if(rg_soal_1.getCheckedRadioButtonId() == R.id.b_1)
            skor +=4;
        if(rg_soal_3.getCheckedRadioButtonId() == R.id.a_3)
            skor+=4;
        if(rg_soal_4.getCheckedRadioButtonId() == R.id.a_4)
            skor+=4;
        if(rg_soal_5.getCheckedRadioButtonId() == R.id.c_5)
            skor+=4;
        if(rg_soal_6.getCheckedRadioButtonId() == R.id.b_6)
            skor+=4;
        if(rg_soal_7.getCheckedRadioButtonId() == R.id.b_7)
            skor+=4;
        if(rg_soal_7.getCheckedRadioButtonId() == R.id.c_8)
            skor+=4;
        if(rg_soal_9.getCheckedRadioButtonId() == R.id.a_9)
            skor+=4;
        if(rg_soal_10.getCheckedRadioButtonId() == R.id.a_10)
            skor+=4;
        if(rg_soal_11.getCheckedRadioButtonId() == R.id.a_11)
            skor+=4;
        if(rg_soal_12.getCheckedRadioButtonId() == R.id.a_12)
            skor+=4;
        if(rg_soal_13.getCheckedRadioButtonId() == R.id.b_13)
            skor+=4;
        if(rg_soal_14.getCheckedRadioButtonId() == R.id.b_14)
            skor+=4;
        if(cb_a_2.isChecked())
            skor+=4;
        if(cb_b_2.isChecked())
            skor+=4;
        if(cb_e_2.isChecked())
            skor+=4;
        if(cb_a_15.isChecked())
            skor+=4;
        if(cb_b_15.isChecked())
            skor+=4;
        if(cb_c_15.isChecked())
            skor+=4;
        if(cb_d_15.isChecked())
            skor+=4;
        if(cb_e_15.isChecked())
            skor+=4;
        if(cb_a_16.isChecked())
            skor+=4;
        if(cb_b_16.isChecked())
            skor+=4;
        if(cb_c_16.isChecked())
            skor+=4;
        if(cb_d_16.isChecked())
            skor+=4;
        if(cb_e_16.isChecked())
            skor+=4;
    }
}
