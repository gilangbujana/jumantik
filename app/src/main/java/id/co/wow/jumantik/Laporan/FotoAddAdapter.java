package id.co.wow.jumantik.Laporan;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

import id.co.wow.jumantik.R;

public class FotoAddAdapter extends RecyclerView.Adapter<FotoAddAdapter.ViewHolder> {

    ArrayList<String> listFoto;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sEditor;
    Context context;
    public FotoAddAdapter(ArrayList<String> listFoto, Context context){
        this.listFoto = listFoto;
        this.context = context;
    }

    public ArrayList<String> getListFoto(){
        return this.listFoto;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_foto, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final File fotoPath = new File(listFoto.get(position)); // Your image file
        Bitmap bitmap = (Bitmap) BitmapFactory.decodeFile(fotoPath.getAbsolutePath());
        holder.iv_add_foto.setImageBitmap(bitmap);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fotoPath.delete()) {
                    listFoto.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listFoto.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_add_foto;
        FloatingActionButton btn_cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_add_foto = (ImageView) itemView.findViewById(R.id.iv_add_foto_item);
            btn_cancel = (FloatingActionButton) itemView.findViewById(R.id.btn_del_foto);
        }
    }
}
