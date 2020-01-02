package id.co.wow.jumantik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import net.glxn.qrgen.android.QRCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class QRCodeActivity extends AppCompatActivity {

    ImageView iv_qr;
    String telp;
    JSONParser jsonParser;
    int id_laporan;
    String pesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        jsonParser = new JSONParser();
        iv_qr = (ImageView) findViewById(R.id.qrcode_kader);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(QRCodeActivity.this);
        try {
            telp = new JSONObject(sp.getString("jsonDataUser", "")).getString("telp");
            new GedLastIDLaporan().execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    void createQRCode(String content) {
            Bitmap bitmap = QRCode.from(content.trim()).bitmap();
            iv_qr.setImageBitmap(bitmap);
        /*WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        QRGEncoder qrgEncoder = new QRGEncoder(content, null, QRGContents.Type.TEXT, smallerDimension);
        qrgEncoder.setColorBlack(Color.RED);
        qrgEncoder.setColorWhite(Color.BLUE);

            // Getting QR-Code as Bitmap
           Bitmap bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            iv_qr.setImageBitmap(bitmap);*/


    }

    class GedLastIDLaporan extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("telp", telp));
                String path = jsonParser.getIP() + "select_last_laporan_user.php";
                JSONObject jsonObject = jsonParser.makeHttpRequest(path, "POST", params);
                pesan = jsonObject.getString("pesan");
                id_laporan = jsonObject.getInt("id_laporan");
            } catch (JSONException e) {
               pesan = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pesan.equals("1")){
                Toast.makeText(QRCodeActivity.this, pesan + id_laporan + "", Toast.LENGTH_SHORT).show();
                createQRCode(id_laporan+"");
            }else {
                Toast.makeText(QRCodeActivity.this, pesan + id_laporan + "", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
