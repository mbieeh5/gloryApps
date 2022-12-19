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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.core.UserData;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AdapterDataService extends RecyclerView.Adapter<AdapterDataService.DataViewHolder> {

    Context context;
    ArrayList<OutputClass> list;
    DatabaseReference db;
    ArrayAdapter<String> adapter;
    String[] items = {"LUNAS", "CANCELED" };

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
        Color a;
        OutputClass oc = list.get(position);
        holder.oTgl.setText(oc.getiTgl());
        holder.oHp.setText(oc.getiHp());
        holder.oRsk.setText(oc.getiRsk());
        holder.oHrg.setText(oc.getiHrg());
        holder.oPenerima.setText(oc.getiPenerima());
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
            holder.eBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdateDialog(oc.getId(), oc.getiTgl(), oc.getiHp(), oc.getiRsk(), oc.getiHrg(), oc.getiPenerima(), oc.getiStatus());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showUpdateDialog(String Id, String iTgl, String iHp, String iRsk, String iHrg, String iPenerima, String iStatus){
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setMargin(50,0,50,0)
                .setContentHolder(new ViewHolder(R.layout.edit_list_layout))
                .setExpanded(false)
                .create();
        View a = (LinearLayout) dialogPlus.getHolderView();

        EditText eTgl = a.findViewById(R.id.eTgl);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        eTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        eTgl.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        EditText eHp = a.findViewById(R.id.eHp);
        EditText eRsk = a.findViewById(R.id.eRsk);
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

        Button btnSimpan = a.findViewById(R.id.btnSimpan);
        dialogPlus.show();
        eTgl.setText(iTgl);
        eHp.setText(iHp);
        eRsk.setText(iRsk);
        eHrg.setText(iHrg);
        eTerima.setText(iPenerima);
        eStatus.setText(iStatus);


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTgl = eTgl.getText().toString();
                String newHp = eHp.getText().toString();
                String newRsk = eRsk.getText().toString();
                String newHrg = eHrg.getText().toString();
                String newTerima = eTerima.getText().toString();
                String newStatus = eStatus.getText().toString();
                db = FirebaseDatabase.getInstance().getReference("Service");
                if(newTgl.isEmpty() || newHp.isEmpty() || newRsk.isEmpty() || newHrg.isEmpty() || newTerima.isEmpty()||newStatus.isEmpty()){
                    Toast.makeText(context, "Pastikan Data Terisi Semua", Toast.LENGTH_SHORT).show();
                }else {
                    db.child("dataService").child(Id).setValue(new OutputClass(Id, newTgl, newHp, newRsk, newHrg, newTerima, newStatus));
                }
                Toast.makeText(context, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();
                dialogPlus.dismiss();
            }
        });
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oTgl, oHp, oRsk, oHrg, oPenerima;
        Button eBtn;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oPenerima = itemView.findViewById(R.id.d_terima);
        oTgl = itemView.findViewById(R.id.d_tgl);
        oHp = itemView.findViewById(R.id.d_hp);
        oRsk = itemView.findViewById(R.id.d_rsk);
        oHrg = itemView.findViewById(R.id.d_hrg);
        eBtn = itemView.findViewById(R.id.editBtn);
        }
    }

}


