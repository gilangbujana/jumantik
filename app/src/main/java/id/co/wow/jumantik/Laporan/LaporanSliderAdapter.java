package id.co.wow.jumantik.Laporan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import id.co.wow.jumantik.R;


public class LaporanSliderAdapter extends SliderViewAdapter<LaporanSliderAdapter.LaporanSliderAdapterVH> {
    private Context context;
    private ArrayList<String> arrRadioButton;

    public LaporanSliderAdapter(Context context){
        this.context = context;
        arrRadioButton = new ArrayList<>();
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");
        arrRadioButton.add("");

    }

    @Override
    public LaporanSliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pernyataan, null);
        return new LaporanSliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(LaporanSliderAdapterVH viewHolder, final int position) {

        int id = viewHolder.rg_jentik.getCheckedRadioButtonId();
        if(id==R.id.rb_aedes){
            arrRadioButton.set(position,""+1);
        }else if(id==R.id.rb_nonaedes){
            arrRadioButton.set(position,""+2);
        }else{
            arrRadioButton.set(position,""+3);
        }

        switch (position){
            case 0: viewHolder.tv_tmpt_laporan.setText("1. Bak Mandi"); break;
            case 1: viewHolder.tv_tmpt_laporan.setText("2. Bak WC"); break;
            case 2: viewHolder.tv_tmpt_laporan.setText("3. Tempayan"); break;
            case 3: viewHolder.tv_tmpt_laporan.setText("4. Ember"); break;
            case 4: viewHolder.tv_tmpt_laporan.setText("5. Dispenser"); break;
            case 5: viewHolder.tv_tmpt_laporan.setText("6. Pot/vas Bunga"); break;
            case 6: viewHolder.tv_tmpt_laporan.setText("7. Kolam/Aquarium"); break;
            case 7: viewHolder.tv_tmpt_laporan.setText("8. Ban Bekas"); break;
            case 8: viewHolder.tv_tmpt_laporan.setText("9. Botol/Kaleng Bekas"); break;
            case 9: viewHolder.tv_tmpt_laporan.setText("10. Lain-lain"); break;
        }

        viewHolder.rg_jentik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_aedes){
                    arrRadioButton.set(position,""+1);
                }else if(i==R.id.rb_nonaedes){
                    arrRadioButton.set(position,""+2);
                }else{
                    arrRadioButton.set(position,""+3);
                }
            }
        });
    }

    public ArrayList<String> getArrRadioButton(){
        return this.arrRadioButton;
    }

    @Override
    public int getCount() {
        return 10;
    }

    class LaporanSliderAdapterVH extends SliderViewAdapter.ViewHolder{
        View itemView;
        TextView tv_tmpt_laporan;
        RadioGroup rg_jentik;

        public LaporanSliderAdapterVH(View itemView) {
            super(itemView);
            rg_jentik = itemView.findViewById(R.id.radiogrup_jentik);
            tv_tmpt_laporan = itemView.findViewById(R.id.tv_tmpt_laporan);
            this.itemView = itemView;
        }
    }
}
