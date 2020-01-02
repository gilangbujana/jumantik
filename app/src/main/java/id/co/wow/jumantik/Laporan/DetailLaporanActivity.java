package id.co.wow.jumantik.Laporan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;


import com.levitnudi.legacytableview.LegacyTableView;

import org.json.JSONException;
import org.json.JSONObject;

//import de.codecrafters.tableview.TableView;
//import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
//import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import id.co.wow.jumantik.R;
import com.smartown.tableview.library.TableView;

public class DetailLaporanActivity extends AppCompatActivity {

    TextView tv_verifikasi_detail, tv_ket_laporan_detail, tv_tgl_laporan_detail;
    TableView ltv_dr, ltv_lr;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        tv_verifikasi_detail = (TextView) findViewById(R.id.tv_verfikasi_detail);
        tv_ket_laporan_detail = (TextView) findViewById(R.id.tv_ket_laporan_detail);
        tv_tgl_laporan_detail = (TextView) findViewById(R.id.tv_tgl_laporan_detail);

       /* ltv_dr = (TableView<String[]>) findViewById(R.id.ltv_dr_detail);
        ltv_lr= (TableView<String[]>) findViewById(R.id.ltv_lr_detail); */

       ltv_dr = (TableView) findViewById(R.id.table_dr_detail);
       ltv_lr= (TableView) findViewById(R.id.table_lr_detail);

        String[] tabHeader = {"Jenis TPN", "Jentik Aedes", "Jentik Non Aedes", "Tidak Ada Jentik"};

        try {

            jsonObject = new JSONObject(getIntent().getStringExtra("detailLaporan"));

            ltv_dr.clearTableContents()
                    .setHeader("Jenis TPN", "Jentik Aedes", "Jentik Non Aedes", "Tidak Ada Jentik")
                    .addContent("BAK MANDI",
                            (jsonObject.getInt("bak_mandi_dr") == 1) ? "YA" : " ",
                            (jsonObject.getInt("bak_mandi_dr") == 2) ? "YA" : " ",
                            (jsonObject.getInt("bak_mandi_dr") == 3) ? "YA" : " ")
                    .refreshTable();

            /*String[][] tabData = {{"BAK MANDI", (jsonObject.getInt("bak_mandi_dr") == 1) ? "YA" : " ",
                    (jsonObject.getInt("bak_mandi_dr") == 2) ? "YA" : " ",
                    (jsonObject.getInt("bak_mandi_dr") == 3) ? "YA" : " "}};
            ltv_dr.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tabHeader));
            ltv_dr.setDataAdapter(new SimpleTableDataAdapter(this, tabData));*/

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
