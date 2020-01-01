package id.co.wow.jumantik.Laporan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import id.co.wow.jumantik.R;
import id.co.wow.jumantik.SignInActivity;

public class UploadActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    FloatingActionButton btn_add_foto;
    private RecyclerView rv_listfoto;
    private FotoAddAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> fotopath;
    SharedPreferences sharedPreferences;

    EditText et_laporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        et_laporan = (EditText) findViewById(R.id.et_laporan);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(sharedPreferences.getString("et_laporan", "")!=null){
            et_laporan.setText(sharedPreferences.getString("et_laporan", ""));
        }
        fotopath = new ArrayList<>();
        if(sharedPreferences.getStringSet("fotopath",null)!=null){
            fotopath.addAll(sharedPreferences.getStringSet("fotopath", null));
        }

        rv_listfoto = (RecyclerView) findViewById(R.id.rv_foto);
        rv_listfoto.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, 2);
        rv_listfoto.setLayoutManager(layoutManager);

        adapter = new FotoAddAdapter(fotopath, UploadActivity.this);
        rv_listfoto.setAdapter(adapter);

        btn_add_foto = (FloatingActionButton) findViewById(R.id.btn_add_pic);
        if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }



        btn_add_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Set<String> set = new HashSet<String>();
        SharedPreferences.Editor sEditor = sharedPreferences.edit();
        this.fotopath = adapter.getListFoto();
        set.addAll(this.fotopath);
        sEditor.putStringSet("fotopath",set);
        sEditor.putString("et_laporan", et_laporan.getText().toString());
        sEditor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpg";
            File folder = new File(Environment.getExternalStorageDirectory() + "/Jumantik");
            File imageFile = new File(Environment.getExternalStorageDirectory() + "/Jumantik", imageFileName);
            if (!folder.exists()) {
                if (folder.mkdir()) {
                    OutputStream os;
                    try {
                        os = new FileOutputStream(imageFile);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 35, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {

                    }
                }
            }else{
                OutputStream os;
                try {
                    os = new FileOutputStream(imageFile);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 35, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {

                }
            }
            fotopath.add(imageFile.getAbsolutePath());
            adapter.notifyDataSetChanged();
        }
    }




}
