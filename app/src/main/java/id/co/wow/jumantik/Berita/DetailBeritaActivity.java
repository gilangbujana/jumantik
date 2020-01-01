package id.co.wow.jumantik.Berita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import id.co.wow.jumantik.R;

public class DetailBeritaActivity extends AppCompatActivity {

    ImageView iv_foto;
    TextView tv_waktu, tv_berita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        iv_foto = (ImageView) findViewById(R.id.iv_foto_berita_detail);
        tv_waktu = (TextView) findViewById(R.id.tv_waktu_berita);
        tv_berita = (TextView) findViewById(R.id.tv_isi_berita);
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("dataBerita"));
            Picasso.get().load("http://jumantikkk.000webhostapp.com/upload/" + jsonObject.getString("foto")).into(iv_foto);
            tv_waktu.setText(jsonObject.getString("tanggal_posting"));
            tv_berita.setText(jsonObject.getString("isi_berita"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
