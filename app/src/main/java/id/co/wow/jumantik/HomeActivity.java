package id.co.wow.jumantik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.PhantomReference;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.wow.jumantik.Berita.ListBeritaActivity;
import id.co.wow.jumantik.Laporan.LaporanActivity;
import id.co.wow.jumantik.Laporan.ListLaporanActivity;
import id.co.wow.jumantik.Quiz.QuizActivity;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String status;
    TextView tv_jk_home;
    ImageView iv_jm_home, iv_ujip;
    LinearLayout ll_qr_code, ll_scanner;
    final int REQUEST_CODE_QR_SCAN = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_jk_home = (TextView) findViewById(R.id.tv_jk_home);
        iv_jm_home = (ImageView) findViewById(R.id.iv_jm_home);
        iv_ujip = (ImageView) findViewById(R.id.iv_ujip_home);
        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code_home);
        ll_scanner = (LinearLayout) findViewById(R.id.ll_scanner_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView tv_nama_nav = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_nama_nav);
        try {
            JSONObject jsonObject = new JSONObject(PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("jsonDataUser", ""));
            tv_nama_nav.setText("Hai, " + jsonObject.getString("namap"));
            status = jsonObject.getString("status");
            if(status.equals("kader")){
                iv_jm_home.setVisibility(View.GONE);
                tv_jk_home.setVisibility(View.VISIBLE);
                iv_ujip.setVisibility(View.GONE);
                ll_qr_code.setVisibility(View.GONE);
                ll_scanner.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void toScanQrCode(View view){
        Intent i = new Intent(HomeActivity.this, QrCodeActivity.class);
        startActivityForResult( i,REQUEST_CODE_QR_SCAN);
    }

    public void whatsapp(String phone) {
        String formattedNumber = phone;
        try{
            Intent sendIntent =new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"");
            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(HomeActivity.this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
            // Handle the camera action
            startActivity(new Intent(HomeActivity.this, ProfilActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));

        } else if (id == R.id.nav_whatsapp) {
            //whatsapp("+6282121246995");
            /*Intent sendIntent =new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"");
            sendIntent.putExtra("jid", "+6282121246995" +"@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");*/
            String url = "https://api.whatsapp.com/send?phone="+"+6282121246995";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            /*
            Alerter.create(HomeActivity.this)
                    .setBackgroundColorRes(R.color.colorPrimaryDark)
                    .setTitle("APAKAH KAMU YAKIN INGIN KELUAR DARI AKUN INI ?")
                    .addButton("YA", R.style.AlertButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).edit().clear().commit();
                            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                            finish();

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
                    .setTitle("APAKAH KAMU YAKIN INGIN KELUAR DARI AKUN INI ?")
                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).edit().clear().commit();
                            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
           // Log.d(LOGTAG,"Have scan result in your app activity :"+ result);
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    }

    public void toInfoActivity(View view){
        startActivity(new Intent(HomeActivity.this, InfoActivity.class));
    }

    public void toListLaporanActivity(View view){
        startActivity(new Intent(HomeActivity.this, ListLaporanActivity.class));
    }

    public void toListBeritaActivity(View view){
        startActivity(new Intent(HomeActivity.this, ListBeritaActivity.class));
    }

    public void toQRActivity(View view){
        startActivity(new Intent(HomeActivity.this, QRCodeActivity.class));
    }

    public void toQuizActivity(View view){
        startActivity(new Intent(HomeActivity.this, QuizActivity.class));
    }
}
