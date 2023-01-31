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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterDataRedeemDonasi extends RecyclerView.Adapter<AdapterDataRedeemDonasi.DataViewHolder> {

    Context context;
    ArrayList<OutputRedeemDonasiClass> listD;
    ArrayAdapter<String> adapter, adapterN;
    DatabaseReference db, dbH;
    String[] nomPulsa = {"5000","10000","20000","50000","100000"};
    String[] items = {"aldi", "amri", "seli", "sindy", "hilda", "wardah", "RRAF" };
    String nomNomPul, Nama;




    public AdapterDataRedeemDonasi(Context context, ArrayList<OutputRedeemDonasiClass> listD) {
        this.context = context;
        this.listD = listD;
    }
    @NonNull
    @Override
    public AdapterDataRedeemDonasi.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_redeem_point, parent, false);
        return new DataViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterDataRedeemDonasi.DataViewHolder holder, int position) {
        OutputRedeemDonasiClass oc = listD.get(position);
        Glide.with(context).load(oc.getgLink()).into(holder.gFoto);
        holder.gJudul.setText(oc.getgJudul());
        holder.gFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentDate = Calendar.getInstance();
                int currentDay = currentDate.get(Calendar.DATE);
                if (currentDay == 1) {
                    muncul(oc.getgJudul(), oc.getPoint(), oc.getNama());
                }else{
                    Toast.makeText(context, "Redeem "+oc.gJudul+" Hanya Dapat Dilakukan Pada Tanggal 1", Toast.LENGTH_SHORT).show();
                }
            }
    });
    }

    @Override
    public int getItemCount() {
        return listD.size();
    }



    private void muncul(String gJudul, Integer point, String nama) {
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.BOTTOM)
                .setMargin(0,0,0,0)
                .setContentHolder(new ViewHolder(R.layout.redeem_point_ke))
                .setExpanded(false)
                .create();
        View a = (LinearLayout) dialogPlus.getHolderView();
        TextView title = a.findViewById(R.id.titleRedeem);
        title.setText(context.getString(R.string.redeem_point_ke, gJudul));
        dialogPlus.show();
        EditText noHp = a.findViewById(R.id.iNoHp);
        noHp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int nomor = s.toString().trim().length();
                if(nomor < 10){
                    noHp.setBackgroundResource(R.drawable.red_border);
                }else {
                    noHp.setBackgroundResource(R.drawable.default_border);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText nom = a.findViewById(R.id.iNominalPoint);
        AutoCompleteTextView atekNama = a.findViewById(R.id.atekNamaPeRedeem);
        final List<String> items = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima").orderByChild("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        String name = ds.child("nama").getValue(String.class);
                        items.add(name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapterN = new ArrayAdapter<String>(context, R.layout.dropdown_list, items);
        atekNama.setAdapter(adapterN);
        atekNama.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String namaRedeem = parent.getItemAtPosition(position).toString();
                Nama = namaRedeem;
            }
        });

        AutoCompleteTextView atekNompul = a.findViewById(R.id.autoCompleteNominal);
        adapter = new ArrayAdapter<String>(context, R.layout.dropdown_list, nomPulsa);
        atekNompul.setAdapter(adapter);
        if(gJudul.equals("PulsaRegular")){
            nom.setVisibility(View.GONE);
            atekNompul.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String nominal = parent.getItemAtPosition(position).toString();
                    nom.setText(nominal);
                }
            });
        }else {
            atekNompul.setVisibility(View.GONE);
        }
        dbH = FirebaseDatabase.getInstance().getReference("Users").child("historyRedeem");
        db = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        Button redeem = a.findViewById(R.id.btnRedeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sn = "SN:"+new Date().getTime();
                String newTujuan = noHp.getText().toString();
                db.orderByChild("nama").equalTo(Nama).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot ds : snapshot.getChildren()){
                                if(ds.child("point").exists() && ds.child("point").getValue() != null){
                                    int PointSekarang = ds.child("point").getValue(Integer.class);
                                    int jmlRedeem = Integer.parseInt(nom.getText().toString());
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                    String Tgl = sdf.format(new Date());
                                    SimpleDateFormat sdj = new SimpleDateFormat("HH:mm:ss");
                                    String jam = sdj.format(new Date());
                                    String newTgl = Tgl +" : "+ jam;
                                    String Status = "BERHASIL";
                                    String Nama = ds.child("nama").getValue().toString();
                                    if(PointSekarang <= jmlRedeem){
                                        Toast.makeText(context, "Point Tidak Mencukupi", Toast.LENGTH_SHORT).show();
                                    }else {
                                        if(Nama.isEmpty() || newTujuan.isEmpty()){
                                            Toast.makeText(context, "MasukanData Dengan Benar", Toast.LENGTH_SHORT).show();
                                        }else {
                                            int PointBaru =  PointSekarang - jmlRedeem;
                                            ds.getRef().child("point").setValue(PointBaru);
                                            dbH.child(sn).setValue(new OutputRedeemHistoryClass(Nama, newTgl, newTujuan, jmlRedeem, gJudul, Status, sn, point ));
                                            Toast.makeText(context, "Berhasil Di Redeem", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    }
                                }
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }


    public static class DataViewHolder extends RecyclerView.ViewHolder{

        ImageView gFoto;
        TextView gJudul;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        gFoto = itemView.findViewById(R.id.IVRedeem);
        gJudul = itemView.findViewById(R.id.judulRedeem);
        }
    }

}


