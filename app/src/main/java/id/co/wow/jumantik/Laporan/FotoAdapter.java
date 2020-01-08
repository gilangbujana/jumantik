package id.co.wow.jumantik.Laporan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;


import id.co.wow.jumantik.R;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {

    JSONArray listFoto;
    Context context;
    public FotoAdapter(JSONArray listFoto, Context context){
        this.listFoto = listFoto;
        this.context = context;
    }

    public JSONArray getListFoto(){
        return this.listFoto;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            String url = "https://jumantikkk.000webhostapp.com/upload/"+listFoto.getString(position);
            Picasso.get().load(url).into(holder.iv_foto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.listFoto.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_foto = (ImageView) itemView.findViewById(R.id.iv_foto_item);
        }
    }
}
