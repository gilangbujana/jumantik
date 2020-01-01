package id.co.wow.jumantik.Berita;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.co.wow.jumantik.Laporan.DetailLaporanActivity;
import id.co.wow.jumantik.R;

public class ListBeritaAdapter extends ShimmerRecyclerView.Adapter<ListBeritaAdapter.ViewHolder> {

    Context context;
    JSONArray jsonArray;
    public ListBeritaAdapter(Context context, JSONArray jsonArray){
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.tv_judul.setText(jsonObject.getString("isi_berita"));
            holder.tv_waktu.setText(jsonObject.getString("tanggal_posting"));
            Picasso.get().load("http://jumantikkk.000webhostapp.com/upload/" + jsonObject.getString("foto")).into(holder.iv_foto);
            final Intent intent = new Intent(context, DetailBeritaActivity.class);
            intent.putExtra("dataBerita", jsonObject.toString());
            holder.ll_item_berita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    class ViewHolder extends ShimmerRecyclerView.ViewHolder{

        TextView tv_judul, tv_waktu;
        ImageView iv_foto, iv_bagikan;
        LinearLayout ll_item_berita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_judul = (TextView) itemView.findViewById(R.id.tv_judul_berita_item);
            tv_waktu = (TextView) itemView.findViewById(R.id.tv_waktu_berita_item);
            iv_foto = (ImageView) itemView.findViewById(R.id.iv_foto_berita_item);
            iv_bagikan = (ImageView) itemView.findViewById(R.id.iv_bagikan_berita_item);
            ll_item_berita = (LinearLayout) itemView.findViewById(R.id.ll_item_berita);
        }
    }
}
