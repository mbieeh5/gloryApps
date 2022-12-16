package com.rraf.gloryservices.adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.activity.HomeActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterDataService extends RecyclerView.Adapter<AdapterDataService.DataViewHolder> {

    Context context;
    ArrayList<OutputClass> list;
    OutputClass getter;
    String db;


    public AdapterDataService(Context context, ArrayList<OutputClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataService.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataService.DataViewHolder holder, int position) {

        OutputClass oc = list.get(position);
        holder.oTgl.setText(oc.getiTgl());
        holder.oHp.setText(oc.getiHp());
        holder.oRsk.setText(oc.getiRsk());
        holder.oHrg.setText(oc.getiHrg());

        db = FirebaseDatabase.getInstance().getReference("Service").child("dataService").getKey();
        String key = getter.getKey();
        holder.eBtn.setOnClickListener(new View.OnClickListener() {

                EditText tgl, hp, rsk, hrg, terima, status;
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.edit_list_layout))
                        .setExpanded(false)
                        .create();

                View holderView = (LinearLayout) dialogPlus.getHolderView();

                tgl = holderView.findViewById(R.id.eTgl);
                hp = holderView.findViewById(R.id.eHp);
                rsk = holderView.findViewById(R.id.eRsk);
                hrg = holderView.findViewById(R.id.eHrg);
                terima = holderView.findViewById(R.id.eTerima);
                status = holderView.findViewById(R.id.eStatus);


                dialogPlus.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oTgl, oHp, oRsk, oHrg;
        Button eBtn;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oTgl = itemView.findViewById(R.id.d_tgl);
        oHp = itemView.findViewById(R.id.d_hp);
        oRsk = itemView.findViewById(R.id.d_rsk);
        oHrg = itemView.findViewById(R.id.d_hrg);
        eBtn = itemView.findViewById(R.id.editBtn);
        }
    }

}


