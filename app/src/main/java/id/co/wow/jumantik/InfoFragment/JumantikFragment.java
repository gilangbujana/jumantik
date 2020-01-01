package id.co.wow.jumantik.InfoFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import id.co.wow.jumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JumantikFragment extends Fragment {


    public JumantikFragment() {
        // Required empty public constructor
    }

    View itemView;
    TextView tv_title_info, tv_konten_info;
    FloatingActionButton btn_definisi, btn_tugas, btn_periksa, btn_tempat, btn_berantas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_jumantik, container, false);
        tv_title_info = (TextView) itemView.findViewById(R.id.tv_title_info_jumantik);
        tv_konten_info = (TextView) itemView.findViewById(R.id.tv_konten_jentik);
        btn_definisi = (FloatingActionButton) itemView.findViewById(R.id.btn_defjum);
        btn_tugas = (FloatingActionButton) itemView.findViewById(R.id.btn_tugas_jumantik);
        btn_periksa = (FloatingActionButton) itemView.findViewById(R.id.btn_pemeriksaan_jentik);
        btn_tempat = (FloatingActionButton) itemView.findViewById(R.id.btn_jenis_tempat_nyamuk);
        btn_berantas = (FloatingActionButton) itemView.findViewById(R.id.btn_pemberantasan_jentik);
        tv_title_info.setText("Definisi Jumantik");
        tv_konten_info.setText(R.string.definisi_jumantik);

        btn_definisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Definisi Jumantik");
                tv_konten_info.setText(R.string.definisi_jumantik);
            }
        });

        btn_tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Tugas Jumantik");
                tv_konten_info.setText(R.string.tugas_jumantik_mandiri);
            }
        });

        btn_periksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Pemeriksaan Jentik");
                tv_konten_info.setText(R.string.cara_pemeriksaan_jentik);
            }
        });

        btn_tempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Jenis Tempat Nyamuk");
                tv_konten_info.setText(R.string.jenis_tempat_nyamuk);
            }
        });

        btn_berantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title_info.setText("Pemberantasan Jentik");
                tv_konten_info.setText(R.string.pemberantasan_jentik);
            }
        });



        return itemView;
    }

}
