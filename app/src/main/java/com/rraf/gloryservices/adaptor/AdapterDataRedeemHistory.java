package com.rraf.gloryservices.adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterDataRedeemHistory extends RecyclerView.Adapter<AdapterDataRedeemHistory.DataViewHolder> {

    Context context;
    ArrayList<OutputRedeemHistoryClass> list;


    public AdapterDataRedeemHistory(Context context, ArrayList<OutputRedeemHistoryClass> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterDataRedeemHistory.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_history_redeem, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataRedeemHistory.DataViewHolder holder, int position) {
        OutputRedeemHistoryClass oc = list.get(position);
        holder.nama.setText(context.getString(R.string.nama_penukar, oc.getNama()));
        holder.hTgl.setText(oc.gethTgl());
        holder.hRedeemKe.setText(context.getString(R.string.penukaran_ke_shopeepay, oc.gethRedeemKe()));
        holder.status.setText(oc.getStatus());
        holder.hTujuan.setText(context.getString(R.string.nomor_tujuan, oc.gethTujuan()));
        holder.sn.setText(oc.getSn());
        //holder.hJmlRedeem.setText(context.getString(R.string.jumlah_penukaran, oc.gethJmlRedeem()));
        String formattedNumber = String.format(Locale.US,"%,d",oc.gethJmlRedeem());
        holder.hJmlRedeem.setText(context.getString(R.string.jumlah_penukaran, formattedNumber));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView nama, hTgl, hTujuan, hRedeemKe, sn, status, hJmlRedeem;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        nama = itemView.findViewById(R.id.tv_nama_penukar);
        hRedeemKe = itemView.findViewById(R.id.tv_tukar_point_ke);
        hJmlRedeem = itemView.findViewById(R.id.tv_jml_point_tukar);
        hTujuan = itemView.findViewById(R.id.tv_no_tujuan);
        sn = itemView.findViewById(R.id.SNHistory);
        status = itemView.findViewById(R.id.Status_history);
        hTgl = itemView.findViewById(R.id.history_tanggal);

        }
    }

}


