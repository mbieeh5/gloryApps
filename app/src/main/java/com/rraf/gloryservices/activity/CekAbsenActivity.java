package com.rraf.gloryservices.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AbsenClass;
import com.rraf.gloryservices.adaptor.AdapteLeaderBoard;
import com.rraf.gloryservices.adaptor.AdapterDataAbsensi;
import com.rraf.gloryservices.adaptor.LeaderboardClass;
import com.rraf.gloryservices.adaptor.OutputClass;
import com.rraf.gloryservices.adaptor.UploadClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CekAbsenActivity extends AppCompatActivity {

    AdapterDataAbsensi adapter;
    ArrayList<AbsenClass> list;
    DatabaseReference Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_absen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        TextView titleAbsen = findViewById(R.id.titleAbsen);
        RecyclerView rsV = findViewById(R.id.recv_Absensi);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(false);
        lm.setReverseLayout(false);
        rsV.setLayoutManager(lm);
        rsV.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataAbsensi(this, list);
        rsV.setAdapter(adapter);
        //Cek Absen Setiap Hari Ini
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sekarang = formater.format(date);
        Db = FirebaseDatabase.getInstance().getReference("Data").child("dataAbsen").child("absensi");
        Db.orderByChild("tanggal").equalTo(sekarang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        list.add(ds.getValue(AbsenClass.class));
                        titleAbsen.setText(getString(R.string.cek_absen_di_sini,"Hari Ini"));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Cek Absen Dari Fab
        FloatingActionButton fab = findViewById(R.id.fabAbsen);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_add_24);
            fab.setImageDrawable(drawable);
            fab.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange_base_250));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(CekAbsenActivity.this, v);
                popup.inflate(R.menu.fab_menu_absensi);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int ID = item.getItemId();
                        if(ID == R.id.fTepatWaktu){
                            Db.orderByChild("nama").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    list.clear();
                                    if(snapshot.exists()){
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            String inputTime = ds.child("jam").getValue(String.class);
                                            String checkTime = "08:30";
                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                            try{
                                                Date inputDate = format.parse(inputTime);
                                                Date checkDate = format.parse(checkTime);
                                                assert inputDate != null;
                                                if(inputDate.before(checkDate) || inputDate.equals(checkDate)){
                                                    list.add(ds.getValue(AbsenClass.class));
                                                    titleAbsen.setText(getString(R.string.cek_absen_di_sini,"Yang Tepat Waktu"));
                                                    adapter.notifyDataSetChanged();
                                               }
                                            }catch (ParseException e){
                                                Log.d(TAG, "error ParsingTime"+e);
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }else if(ID == R.id.fTerlambat){
                            Db.orderByChild("nama").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    list.clear();
                                    if (snapshot.exists()) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            String inputTime = ds.child("jam").getValue(String.class);
                                            String checkTime = "08:30";
                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                            try {
                                                Date inputDate = format.parse(inputTime);
                                                Date checkDate = format.parse(checkTime);
                                                assert inputDate != null;
                                                if (inputDate.after(checkDate)) {
                                                    list.add(ds.getValue(AbsenClass.class));
                                                    titleAbsen.setText(getString(R.string.cek_absen_di_sini, "Yang Terlambat"));
                                                    adapter.notifyDataSetChanged();
                                                }
                                            } catch (ParseException e) {
                                                Log.d(TAG, "error ParsingTime" + e);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }else if(ID == R.id.fLainnya){
                            DialogPlus dialogPlus = DialogPlus.newDialog(CekAbsenActivity.this)
                                    .setGravity(Gravity.CENTER)
                                    .setMargin(50, 0, 50, 0)
                                    .setContentHolder(new ViewHolder(R.layout.sort_item_absen))
                                    .setExpanded(false)
                                    .create();
                            View a = (LinearLayout) dialogPlus.getHolderView();
                            dialogPlus.show();
                            TextView fTgl = a.findViewById(R.id.fTgl);
                            fTgl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    DatePickerDialog datePickerDialog =  new DatePickerDialog(
                                            CekAbsenActivity.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year);
                                            fTgl.setText(date);
                                        }
                                    },year,month,day);
                                    datePickerDialog.show();
                                }
                            });
                            Button cari = a.findViewById(R.id.btnCari);
                            Button btnCari = a.findViewById(R.id.btnCariType);
                            btnCari.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Query query = Db.orderByChild("tanggal");
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            list.clear();
                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                AbsenClass oclass = ds.getValue(AbsenClass.class);
                                                if(oclass != null){
                                                    list.add(oclass);
                                                    titleAbsen.setText(getString(R.string.cek_absen_di_sini, "Semua Data"));
                                                }
                                                popup.dismiss();
                                                dialogPlus.dismiss();
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                            cari.setOnClickListener(new View.OnClickListener() {
                                final int total = 0;
                                @Override
                                public void onClick(View v) {
                                    list.removeAll(list);
                                    Query query = Db.orderByChild("tanggal").equalTo(fTgl.getText().toString());
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                list.clear();
                                                for(DataSnapshot ds : snapshot.getChildren()){
                                                    AbsenClass oclass = ds.getValue(AbsenClass.class);
                                                    list.add(oclass);
                                                    popup.dismiss();
                                                    dialogPlus.dismiss();
                                                }
                                                titleAbsen.setText(getString(R.string.cek_absen_di_sini,"per Tanggal: "+fTgl.getText().toString()));
                                                adapter.notifyDataSetChanged();
                                            }else {
                                                Toast.makeText(CekAbsenActivity.this, "Data Tidak DiTemukan", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
}