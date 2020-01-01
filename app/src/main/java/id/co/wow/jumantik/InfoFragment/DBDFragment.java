package id.co.wow.jumantik.InfoFragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.co.wow.jumantik.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DBDFragment extends Fragment {


    public DBDFragment() {
        // Required empty public constructor
    }

    View itemView;
    TextView tv_title_info, tv_konten_info;
    FloatingActionButton btn_definisi, btn_gejala, btn_penularan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_dbd, container, false);
        tv_title_info = (TextView) itemView.findViewById(R.id.tv_title_info_dbd);
        tv_konten_info = (TextView) itemView.findViewById(R.id.tv_konten_dbd);
        btn_definisi = (FloatingActionButton) itemView.findViewById(R.id.btn_defdbd);
        btn_gejala = (FloatingActionButton) itemView.findViewById(R.id.btn_gejala_dbd);
        btn_penularan = (FloatingActionButton) itemView.findViewById(R.id.btn_penularan_dbd);
        tv_title_info.setText("Definisi DBD");
        tv_konten_info.setText(R.string.definisi_dbd);


        btn_definisi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Definisi DBD");
                tv_konten_info.setText(R.string.definisi_dbd);
                btn_definisi.setBackgroundColor(getResources().getColor(R.color.white));
                btn_gejala.setBackgroundColor(getResources().getColor(R.color.mainColor));
                btn_penularan.setBackgroundColor(getResources().getColor(R.color.mainColor));
            }
        });

        btn_penularan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tv_title_info.setText("Penularan DBD");
                tv_konten_info.setText(R.string.penularan_dbd);
                btn_definisi.setBackgroundColor(getResources().getColor(R.color.mainColor));
                btn_gejala.setBackgroundColor(getResources().getColor(R.color.mainColor));
                btn_penularan.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        btn_gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Gejala DBD");
                tv_konten_info.setText(R.string.gejala_dbd);
                btn_definisi.setBackgroundColor(getResources().getColor(R.color.mainColor));
                btn_gejala.setBackgroundColor(getResources().getColor(R.color.white));
                btn_penularan.setBackgroundColor(getResources().getColor(R.color.mainColor));
            }
        });

        return itemView;
    }

}
