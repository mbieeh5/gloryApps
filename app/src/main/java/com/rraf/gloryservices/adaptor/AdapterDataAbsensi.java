package com.rraf.gloryservices.adaptor;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.rraf.gloryservices.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterDataAbsensi extends RecyclerView.Adapter<AdapterDataAbsensi.DataViewHolder> {

    Context context;
    ArrayList<AbsenClass> list;
    DatabaseReference db, dbH;


    public AdapterDataAbsensi(Context context, ArrayList<AbsenClass> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterDataAbsensi.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_absensi, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataAbsensi.DataViewHolder holder, int position) {
        AbsenClass oc = list.get(position);
        Glide.with(context).load(oc.getImageUrl()).into(holder.fotoAbsen);
        holder.nama.setText(context.getString(R.string.nama_s, oc.getNama()));
        holder.tanggal.setText(context.getString(R.string.tanggal_s, oc.getTanggal()));
        holder.jam.setText(context.getString(R.string.jam_s,oc.getJam()));
        String inputTime = oc.getJam();
        String checkTime = "08:30";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            try{
                Date inputDate = format.parse(inputTime);
                Date checkDate = format.parse(checkTime);
                if(inputDate.after(checkDate)){
            holder.cvAbsen.setCardBackgroundColor(context.getColor(R.color.merahGagal));
            holder.status.setText(context.getString(R.string.status_keharian_s, "TELAT LU WKWKWK"));
                }else{
            holder.cvAbsen.setCardBackgroundColor(context.getColor(R.color.ijoSukses));
            holder.status.setText(context.getString(R.string.status_keharian_s, "CIE GA TELAT"));
                }
            }catch (ParseException e){
                Log.d(TAG, "error ParsingTime"+e);
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView nama, tanggal, jam, status;
        ImageView fotoAbsen;
        CardView cvAbsen;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        cvAbsen = itemView.findViewById(R.id.cvAbsen);
        fotoAbsen = itemView.findViewById(R.id.imageAbsen);
        nama = itemView.findViewById(R.id.namaAbsen);
        tanggal = itemView.findViewById(R.id.tanggalAbsen);
        jam = itemView.findViewById(R.id.jamAbsen);
        status = itemView.findViewById(R.id.statusAbsen);
        }
    }

}


