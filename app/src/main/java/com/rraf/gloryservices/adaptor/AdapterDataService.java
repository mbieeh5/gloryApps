package com.rraf.gloryservices.adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterDataService extends RecyclerView.Adapter<AdapterDataService.DataViewHolder> {

    Context context;
    ArrayList<OutputClass> list;
    DatabaseReference db;
    ArrayAdapter<String> adapter;
    String[] items = {"LUNAS", "CANCELED" };
    String[] itemP = {"AMRI", "IBNU" };


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
        holder.oTglK.setText(oc.getiTglK());
        holder.oKerjaan.setText(oc.getiKerjaan());
        holder.oHp.setText(oc.getiHp());
        holder.oRsk.setText(oc.getiRsk());
        holder.oHrg.setText(oc.getiHrg());
        holder.oMdl.setText(oc.getiMdl());
        holder.oPenerima.setText(oc.getiPenerima());
        holder.oCuan.setText(" ");

        if(Objects.equals(oc.iStatus, "LUNAS")) {
            holder.eBtn.setText(oc.iStatus);
            holder.eBtn.setTextColor(Color.rgb(255,255,255));
            holder.eBtn.setBackgroundColor(Color.rgb(0, 255 ,0));
            holder.eBtn.setEnabled(false);
        }else if(Objects.equals(oc.iStatus, "CANCELED")){
            holder.eBtn.setText(oc.iStatus);
            holder.eBtn.setTextColor(Color.rgb(255,255,255));
            holder.eBtn.setBackgroundColor(Color.rgb(255, 0 ,0));
            holder.eBtn.setEnabled(false);
        }else{
            holder.eBtn.setText("EDIT");
            holder.eBtn.setEnabled(true);
            holder.eBtn.setBackgroundColor(Color.rgb(245,130,22));
            holder.eBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdateDialog(oc.getId(), oc.getiTgl(), oc.getiTglK(), oc.getiHp(), oc.getiRsk(), oc.getiMdl(), oc.getiHrg(), oc.getiPenerima(), oc.getiStatus(), oc.getiKerjaan());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showUpdateDialog(String Id, String iTgl, String iTglK, String iHp, String iRsk,String iMdl, String iHrg, String iPenerima, String iStatus, String iKerjaan){
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.BOTTOM)
                .setMargin(0,0,0,0)
                .setContentHolder(new ViewHolder(R.layout.edit_list_layout))
                .setExpanded(false)
                .create();
        View a = (LinearLayout) dialogPlus.getHolderView();

        EditText eTgl = a.findViewById(R.id.eTgl);
        CardView cTglK = a.findViewById(R.id.cveTglK);
        TextView eTglK = a.findViewById(R.id.eTglK);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        cTglK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        eTglK.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        EditText eHp = a.findViewById(R.id.eHp);
        EditText eRsk = a.findViewById(R.id.eRsk);
        EditText eMdl = a.findViewById(R.id.eMdl);
        EditText eHrg = a.findViewById(R.id.eHrg);
        EditText eTerima = a.findViewById(R.id.eTerima);
        AutoCompleteTextView eStatus = a.findViewById(R.id.eStatus);
        adapter = new ArrayAdapter<String>(context, R.layout.dropdown_list, items);
        eStatus.setAdapter(adapter);
        eStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                eStatus.setText(item);
            }
        });
        AutoCompleteTextView eKerjaan = a.findViewById(R.id.eKerjaan);
        adapter = new ArrayAdapter<String>(context, R.layout.dropdown_list, itemP);
        eKerjaan.setAdapter(adapter);
        eKerjaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                eKerjaan.setText(item);
                if(Objects.equals(item, "IBNU")) {
                    eMdl.setEnabled(true);
                }else if(Objects.equals(item, "AMRI")){
                    eMdl.setEnabled(false);
                } else{
                    eMdl.setEnabled(false);
                }
            }
        });

        Button btnSimpan = a.findViewById(R.id.btnSimpan);
        eTgl.setText(iTgl);
        eHp.setText(iHp);
        eRsk.setText(iRsk);
        eHrg.setText(iHrg);
        eTerima.setText(iPenerima);
        eKerjaan.setText(iKerjaan);
        eStatus.setText(iStatus);
        eTglK.setText(iTglK);
        dialogPlus.show();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTgl = eTgl.getText().toString();
                String newTglK = eTglK.getText().toString();
                String newHp = eHp.getText().toString();
                String newRsk = eRsk.getText().toString();
                String newMdl = eMdl.getText().toString();
                String newHrg = eHrg.getText().toString();
                String newTerima = eTerima.getText().toString();
                String newKerjaan = eKerjaan.getText().toString();
                String newStatus = eStatus.getText().toString();
                db = FirebaseDatabase.getInstance().getReference("Service");
                db.child("dataService").child(Id).setValue(new OutputClass(Id, newTgl, newTglK, newHp, newRsk,newMdl, newHrg, newTerima, newStatus, newKerjaan));
                    Toast.makeText(context, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    dialogPlus.dismiss();
            }
        });
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oTgl, oHp, oRsk, oHrg, oPenerima, oMdl, oCuan, oKerjaan, oTglK;
        Button eBtn;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oPenerima = itemView.findViewById(R.id.d_terima);
        oKerjaan = itemView.findViewById(R.id.d_kerjaan);
        oTgl = itemView.findViewById(R.id.d_tgl);
        oTglK = itemView.findViewById(R.id.d_tglK);
        oHp = itemView.findViewById(R.id.d_hp);
        oRsk = itemView.findViewById(R.id.d_rsk);
        oMdl = itemView.findViewById(R.id.d_mdl);
        oHrg = itemView.findViewById(R.id.d_hrg);
        oCuan = itemView.findViewById(R.id.total);
        eBtn = itemView.findViewById(R.id.editBtn);
        }
    }

}


