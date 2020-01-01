package id.co.wow.jumantik.Laporan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import id.co.wow.jumantik.R;

public class RiwayatLaporanAdapter extends ShimmerRecyclerView.Adapter<RiwayatLaporanAdapter.ViewHolder>{

    JSONArray jsonArray;
    Context context;

    public RiwayatLaporanAdapter(JSONArray jsonArray, Context context){
        this.jsonArray = jsonArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_laporan, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String tgl_laporan = jsonArray.getJSONObject(position).getString("tgl_laporan");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            long time = sdf.parse(tgl_laporan).getTime();
            PrettyTime prettyTime = new PrettyTime(new Locale("id"));
            //tgl_laporan = tgl_laporan.split(" ")[0];
            String ago = prettyTime.format(new Date(time));
            holder.tv_tgl_laporan.setText("LAPORAN " + ago);

            int statusLaporan = jsonArray.getJSONObject(position).getInt("status");
            if(statusLaporan==1){
                holder.tv_status_laporan.setBackgroundResource(R.drawable.verified);
                holder.tv_status_laporan.setText("SUDAH DIVERIFIKASI");
            }else{
                holder.tv_status_laporan.setBackgroundResource(R.drawable.unverified);
                holder.tv_status_laporan.setText("BELUM DIVERIFIKASI");
            }
            final Intent intent = new Intent(context, DetailLaporanActivity.class);
            intent.putExtra("detailLaporan", jsonArray.getJSONObject(position).toString());
            holder.tv_klik_laporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    class ViewHolder extends ShimmerRecyclerView.ViewHolder{

        TextView tv_tgl_laporan, tv_klik_laporan, tv_status_laporan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tgl_laporan = (TextView) itemView.findViewById(R.id.tv_tgllaporan_item);
            tv_klik_laporan = (TextView) itemView.findViewById(R.id.tv_kliklaporan_item);
            tv_status_laporan = (TextView) itemView.findViewById(R.id.tv_statuslaporan_item);
        }
    }
}
