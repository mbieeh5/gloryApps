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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterDataTransfer extends RecyclerView.Adapter<AdapterDataTransfer.DataViewHolder> {

    Context context;
    ArrayList<OutputTfClass> list;
    DatabaseReference db;
    ArrayAdapter<String> adapter;
    String[] items = {"LUNAS", "CANCELED"};


    public AdapterDataTransfer(Context context, ArrayList<OutputTfClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataTransfer.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_transfer, parent, false);
        return new DataViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterDataTransfer.DataViewHolder holder, int position) {
        OutputTfClass oc = list.get(position);
        String number = oc.getiNom().toString();
        DecimalFormat decimalFormat = new DecimalFormat("##,###,###");
        holder.oBank.setText(oc.getiBank());
        holder.oNama.setText(oc.getiNama());
        holder.oNom.setText(decimalFormat.format(Integer.parseInt(number)));
        holder.oNoRek.setText(oc.getiNorek());
        holder.oTfMana.setText(oc.getiTfMana());
        holder.oTgl.setText(oc.getiTgl());
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
                    showUpdateDialog(oc.getiNama(), oc.getId(), oc.getiNom(), oc.getiBank(), oc.getiStatus(), oc.getiNorek(), oc.getiTgl(), oc.getiTfMana());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showUpdateDialog(String iNama, String Id, Integer iNom, String iBank, String iStatus, String iNorek, String iTgl, String iTfMana){
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.BOTTOM)
                .setMargin(0,0,0,0)
                .setContentHolder(new ViewHolder(R.layout.edit_list_layout_transfer))
                .setExpanded(false)
                .create();
        View a = (LinearLayout) dialogPlus.getHolderView();
        EditText eTgl = a.findViewById(R.id.eTgl);
        EditText eNama = a.findViewById(R.id.eNama);
        EditText eBank = a.findViewById(R.id.eBank);
        EditText eNorek = a.findViewById(R.id.eNorek);
        EditText eNom = a.findViewById(R.id.eNom);
        EditText eTfmana = a.findViewById(R.id.eTfMana);
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
        Button btnSimpan = a.findViewById(R.id.btnSimpanTf);
        eTgl.setText(iTgl);
        eNama.setText(iNama);
        eBank.setText(iBank);
        eNorek.setText(iNorek);
        eNom.setText(String.format(iNom.toString(),regExp));
        eTfmana.setText(iTfMana);
        eStatus.setText(iStatus);
        dialogPlus.show();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTgl = eTgl.getText().toString();
                String newNama = eNama.getText().toString();
                String newBank = eBank.getText().toString();
                String newNorek = eNorek.getText().toString();
                Integer newNom = Integer.parseInt(eNom.getText().toString());
                String newTfMana = eTfmana.getText().toString();
                String newStatus = eStatus.getText().toString();
                db = FirebaseDatabase.getInstance().getReference("Transfer");
                db.child("dataTransfer").child(Id).setValue(new OutputTfClass(newNama,Id, newNom , newBank, newStatus,  newNorek, newTgl, newTfMana));
                    Toast.makeText(context, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    dialogPlus.dismiss();
            }
        });
    }
    final String regExp = "[0-9]+([,.][0-9]{1,2})?";


    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oTgl, oNama, oBank, oNoRek, oNom, oTfMana;
        Button eBtn;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oTgl = itemView.findViewById(R.id.d_tglTf);
        oNama = itemView.findViewById(R.id.d_namaTf);
        oBank = itemView.findViewById(R.id.d_bank);
        oNoRek = itemView.findViewById(R.id.d_noRek);
        oNom = itemView.findViewById(R.id.d_nominal);
        oTfMana = itemView.findViewById(R.id.d_punya);
        eBtn = itemView.findViewById(R.id.editBtnTf);
        }
    }

}


