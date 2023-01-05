package com.rraf.gloryservices.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rraf.gloryservices.R;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterDataServiceHistory extends RecyclerView.Adapter<AdapterDataServiceHistory.DataViewHolder> {

    Context context;
    ArrayList<OutputClass> list;

    public AdapterDataServiceHistory(Context context, ArrayList<OutputClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataServiceHistory.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_history, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataServiceHistory.DataViewHolder holder, int position) {
        OutputClass oc = list.get(position);
        holder.oTgl.setText(oc.getiTgl());
        holder.oTglK.setText(oc.getiTglK());
        holder.oKerjaan.setText(oc.getiKerjaan());
        holder.oHp.setText(oc.getiHp());
        holder.oRsk.setText(oc.getiRsk());
        holder.oHrg.setText(oc.getiHrg());
        holder.oPenerima.setText(oc.getiPenerima());
        if(Objects.equals(oc.iStatus, "LUNAS")) {
            holder.ostatusTxt.setText(oc.iStatus);
            holder.ostatusTxt.setTextColor(Color.rgb(255,255,255));
            holder.ostatusTxt.setBackgroundColor(Color.rgb(0, 255 ,0));
        }else if(Objects.equals(oc.iStatus, "CANCELED")){
            holder.ostatusTxt.setText(oc.iStatus);
            holder.ostatusTxt.setTextColor(Color.rgb(255,255,255));
            holder.ostatusTxt.setBackgroundColor(Color.rgb(255, 0 ,0));
        }else{
            holder.ostatusTxt.setText("PROSES");
            holder.ostatusTxt.setBackgroundColor(Color.rgb(245,130,22));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oTgl, oHp, oRsk, oHrg, oPenerima, oKerjaan, ostatusTxt, oTglK ;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oTglK = itemView.findViewById(R.id.d_tglK);
        oPenerima = itemView.findViewById(R.id.d_terima);
        oKerjaan = itemView.findViewById(R.id.d_kerjaan);
        oTgl = itemView.findViewById(R.id.d_tgl);
        oHp = itemView.findViewById(R.id.d_hp);
        oRsk = itemView.findViewById(R.id.d_rsk);
        oHrg = itemView.findViewById(R.id.d_hrg);
        ostatusTxt = itemView.findViewById(R.id.statusTxt);
        }
    }

}


