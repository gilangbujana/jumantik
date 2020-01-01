package id.co.wow.jumantik.Laporan;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
//import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.wow.jumantik.R;
import id.co.wow.jumantik.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LuarRumahFragment extends Fragment {


    public LuarRumahFragment() {
        // Required empty public constructor
    }

    View itemView;
    FloatingActionButton btn_add_foto_dan_laporan;
    LaporanSliderAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        itemView = inflater.inflate(R.layout.fragment_luar_rumah, container, false);

        SliderView sliderView = itemView.findViewById(R.id.imageSlider_laporan);
        btn_add_foto_dan_laporan = (FloatingActionButton) itemView.findViewById(R.id.btn_add_foto_dan_laporan);
        adapter = new LaporanSliderAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(R.color.mainColor);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.setAutoCycle(false);
        sliderView.startAutoCycle();

        btn_add_foto_dan_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UploadActivity.class));
            }
        });

        /*
        btn_simpan_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> set = new HashSet<>();
                set.addAll(adapter.getArrRadioButton());
                sEditor.putStringSet("rgLR", set);
                sEditor.commit();
                alerter(adapter.getArrRadioButton().toString() + "\n", false, true, true);
            }
        });
        */

        return itemView;
    }

    ProgressDialog pDialog;
    public void alerter(String pesan, Boolean progress, Boolean dissmissable, Boolean infinite){
        /*Alerter.create(getActivity())
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .setText(pesan.toUpperCase())
                .enableProgress(progress)
                .enableInfiniteDuration(infinite)
                .setDismissable(dissmissable)
                .show();*/

        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle(pesan);
        pDialog.setCancelable(dissmissable);
        pDialog.show();
    }

}
