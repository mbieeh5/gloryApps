package com.rraf.gloryservices.adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
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

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                tgl = holderView.findViewById(R.id.eTgl);
                tgl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog =  new DatePickerDialog(
                                context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month+1;
                                String date = day+"/"+month+"/"+year;
                                tgl.setText(date);
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

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


